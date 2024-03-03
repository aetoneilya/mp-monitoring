package com.machrist.mpmonitoring.metric.storage

import com.clickhouse.client.ClickHouseClient
import com.clickhouse.client.ClickHouseNodes
import com.clickhouse.client.ClickHouseProtocol
import com.clickhouse.data.ClickHouseCompression
import com.clickhouse.data.ClickHouseFormat
import com.machrist.mpmonitoring.metric.model.MetricProject
import org.springframework.stereotype.Repository

@Repository
class MetricStorageClickHouse(
    private val clickHouseNodes: ClickHouseNodes
) : MetricStorage {

    companion object {
        const val CREATE_TABLE_QUERY = """
                                CREATE TABLE :table_name
                                (
                                    `service` LowCardinality(String),
                                    `time` DateTime CODEC(Delta(4), ZSTD(1)),
                                    `value` UInt32
                                )
                                ENGINE = MergeTree
                                ORDER BY (service, time)
                                """
    }

    override fun createProject(project: MetricProject): Unit =
        ClickHouseClient.newInstance(ClickHouseProtocol.HTTP).use { client ->
            client.read(clickHouseNodes)
                .format(ClickHouseFormat.RowBinaryWithNamesAndTypes)
                .query(CREATE_TABLE_QUERY)
                .params(
                    mapOf(
                        "table_name" to project.name
                    )
                )
                .compressServerResponse(false)
                .execute()
        }
}