package com.machrist.mpmonitoring.metric.storage

import com.clickhouse.client.ClickHouseNodes
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "clickhouse")
class ClickHouseStorageConfiguration {
    lateinit var host: String
    lateinit var port: String
    lateinit var database: String
    lateinit var username: String
    lateinit var password: String


    @Bean
    fun clickHouseNodes(): ClickHouseNodes =
        ClickHouseNodes.of("http://$username:$password@$host:$port")
}