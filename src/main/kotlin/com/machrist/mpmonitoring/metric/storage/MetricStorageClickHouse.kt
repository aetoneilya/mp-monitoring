package com.machrist.mpmonitoring.metric.storage

import com.clickhouse.client.ClickHouseClient
import com.clickhouse.client.ClickHouseConfig
import com.clickhouse.client.ClickHouseNodes
import com.clickhouse.client.ClickHouseProtocol
import com.clickhouse.client.ClickHouseResponse
import com.clickhouse.data.ClickHouseColumn
import com.clickhouse.data.ClickHouseDataStreamFactory
import com.clickhouse.data.ClickHouseFormat
import com.machrist.mpmonitoring.common.logger
import com.machrist.mpmonitoring.metric.model.MetricProject
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
                                    `value` Double  CODEC(Gorilla)
                                )
                                ENGINE = MergeTree
                                PARTITION BY toYYYYMM(time)
                                ORDER BY (sensor, time)
                                SETTINGS index_granularity = 8192
                                """
        const val TABLE_EXISTS = """
            EXISTS :table_name
        """

    }

    override fun createProject(project: MetricProject) {
        log.info("creating project ${project.name}")
        ClickHouseClient.newInstance(ClickHouseProtocol.HTTP).use { client ->
            client.read(clickHouseNodes)
                .format(ClickHouseFormat.RowBinaryWithNamesAndTypes)
                .query(CREATE_TABLE_QUERY)
                .params(mapOf("table_name" to project.name))
                .execute()
        }

    }

    override fun projectExists(project: MetricProject): Boolean {
        log.info("checking project existance ${project.name}")
        val result = ClickHouseClient.newInstance(ClickHouseProtocol.HTTP).read(clickHouseNodes)
            .format(ClickHouseFormat.RowBinaryWithNamesAndTypes)
            .query(TABLE_EXISTS)
            .params(mapOf("table_name" to project.name))
            .executeAndWait()

        return result.firstRecord().getValue(0).asInteger() > 0
    }

    override fun storeMetric(project: MetricProject, timeSeries: TimeSeries) {

        val columns =
            ClickHouseColumn.parse("`sensor` LowCardinality(String), `time` DateTime CODEC(Delta(4), ZSTD(1)),  `value` Double")

        log.info("store metrics to ${project.name}")

        ClickHouseClient.newInstance(ClickHouseProtocol.HTTP).use { client ->
            val request = client
                .read(clickHouseNodes)
                .write()
                .table(project.name)
                .format(ClickHouseFormat.RowBinary)

            val config: ClickHouseConfig = request.config
            var future: CompletableFuture<ClickHouseResponse?>


            ClickHouseDataStreamFactory.getInstance()
                .createPipedOutputStream(config).use { stream ->
                    // in async mode, which is default, execution happens in a worker thread
                    future = request.data(stream.inputStream).execute()

                    val processor = ClickHouseDataStreamFactory
                        .getInstance()
                        .getProcessor(
                            config,
                            null,
                            stream,
                            null,
                            columns
                        )

                    val values = columns.map { it.newValue(config) }
                    val serializers = processor.getSerializers(config, columns)
                    for (datapoint in timeSeries.timeSeriesPoints) {
                        serializers[0].serialize(values[0].update(timeSeries.sensorId), stream)
                        serializers[1].serialize(values[1].update(datapoint.dateTime), stream)
                        serializers[2].serialize(values[2].update(datapoint.value), stream)
                    }
                }

            future.get().use { response ->
                val summary = response!!.summary
                log.info("total written rows $summary")
            }
        }

    }
}