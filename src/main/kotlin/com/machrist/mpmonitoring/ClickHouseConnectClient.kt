package com.machrist.mpmonitoring

import com.clickhouse.client.ClickHouseClient
import com.clickhouse.client.ClickHouseNodes
import com.clickhouse.client.ClickHouseProtocol
import com.clickhouse.data.ClickHouseFormat


const val DB_URL_FOR_CLIENT = "http://$DB_USER:$DB_PASS@$DB_HOST:8443/$DB_NAME?ssl=1&sslmode=strict&sslrootcert=$CA_CERT"
fun main() {
    println(DB_URL_FOR_CLIENT)
    val servers = ClickHouseNodes.of(
        DB_URL_FOR_CLIENT
    )

    ClickHouseClient.newInstance(ClickHouseProtocol.HTTP).use { client ->
        client.read(servers)
            .format(ClickHouseFormat.RowBinaryWithNamesAndTypes)
            .query("select * from numbers(:limit)")
            .params(1000)
            .executeAndWait().use { response ->
                val summary = response.summary
                val totalRows = summary.totalRowsToRead
            }
    }
}
