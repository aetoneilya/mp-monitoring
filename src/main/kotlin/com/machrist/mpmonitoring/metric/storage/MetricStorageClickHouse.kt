package com.machrist.mpmonitoring.metric.storage

import com.clickhouse.client.*
import com.clickhouse.data.ClickHouseColumn
import com.clickhouse.data.ClickHouseDataStreamFactory
import com.clickhouse.data.ClickHouseFormat
import com.machrist.mpmonitoring.common.logger
import com.machrist.mpmonitoring.metric.model.TimeSeries
import org.springframework.stereotype.Repository
import java.util.concurrent.CompletableFuture


@Repository
class MetricStorageClickHouse(
    private val clickHouseNodes: ClickHouseNodes
) : MetricStorage {
    val log by logger()

    companion object {
        const val CREATE_TABLE_QUERY = """
                                CREATE TABLE :table_name
                                (
                                    `sensor` LowCardinality(String),
                                    `time` DateTime CODEC(Delta(4), ZSTD(1)),
                                    `value` Double
                                )
                                ENGINE = MergeTree
                                ORDER BY (sensor, time)
                                """
        //replace with collapsing merge tree

    }

    override fun createProject(projectName: String) {
        try {
            ClickHouseClient.newInstance(ClickHouseProtocol.HTTP).use { client ->
                client.read(clickHouseNodes)
                    .format(ClickHouseFormat.RowBinaryWithNamesAndTypes)
                    .query(CREATE_TABLE_QUERY)
                    .params(mapOf("table_name" to projectName))
                    .executeAndWait()
            }
        } catch (e: ClickHouseException) {
            log.error(e.toString());
        }
    }

    override fun storeMetric(projectName: String, timeSeries: TimeSeries) {

        if (timeSeries.timeSeriesPoints == null) {
            return
        }

        val columns =
            ClickHouseColumn.parse("`sensor` LowCardinality(String), `time` DateTime CODEC(Delta(4), ZSTD(1)),  `value` Double")

        log.info("store metrics to $projectName")
        try {
            ClickHouseClient.newInstance(ClickHouseProtocol.HTTP).use { client ->
                val request = client.read(clickHouseNodes).write()
                    .table(projectName).format(ClickHouseFormat.RowBinary)
                val config: ClickHouseConfig = request.config
                var future: CompletableFuture<ClickHouseResponse?>


                ClickHouseDataStreamFactory.getInstance()
                    .createPipedOutputStream(config, null as Runnable?).use { stream ->
                        // in async mode, which is default, execution happens in a worker thread
                        future = request.data(stream.inputStream).execute()

                        val processor = ClickHouseDataStreamFactory.getInstance().getProcessor(
                            config, null,
                            stream, null, columns
                        )

                        val values = columns.map { it.newValue(config) }
                        val serializers = processor.getSerializers(config, columns)
                        for (datapoint in timeSeries.timeSeriesPoints) {
                            serializers[0].serialize(values[0].update(timeSeries.sensor.id), stream)
                            serializers[1].serialize(values[1].update(datapoint.dateTime), stream)
                            serializers[2].serialize(values[2].update(datapoint.value), stream)
                        }
                    }

                future.get().use { response ->
                    val summary = response!!.summary
                    log.info("total written rows $summary")
                }
            }
        } catch (e: ClickHouseException) {
            log.error(e.toString());
        }
    }
}