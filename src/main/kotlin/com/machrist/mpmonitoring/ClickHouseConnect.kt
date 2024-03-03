package com.machrist.mpmonitoring

import java.sql.DriverManager

const val DB_HOST = "rc1a-r43o2hcnpg7llmbq.mdb.yandexcloud.net"
const val DB_NAME = "db"
const val DB_USER = "machrist"
const val DB_PASS = "30250Begemot!binBash"
const val CA_CERT = "/usr/local/share/ca-certificates/Yandex/RootCA.crt"

const val DB_URL = "jdbc:clickhouse://$DB_HOST:8443/$DB_NAME?ssl=1&sslmode=strict&sslrootcert=$CA_CERT"

const val CREATE_TABLE_QUERY = """
                                CREATE TABLE optimized_wikistat
                                (
                                    `time` DateTime CODEC(Delta(4), ZSTD(1)),
                                    `project` LowCardinality(String),
                                    `subproject` LowCardinality(String),
                                    `sensor` String,
                                    `value` UInt32
                                )
                                ENGINE = MergeTree
                                ORDER BY (path, time)
                                """

fun main(args: Array<String>) {
//    val properties = Properties()
//    val dataSource = ClickHouseDataSource(DB_URL, properties)
//    dataSource.getConnection(DB_USER, DB_PASS)

    println(DB_URL)
    try {
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)
        val rs = conn.createStatement().executeQuery("SELECT version()")
        if (rs.next()) {
            println(rs.getString(1))
        }

        conn.close()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

