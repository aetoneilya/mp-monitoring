package com.machrist.mpmonitoring

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
@OpenAPIDefinition(info = Info(title = "Url Shortener", version = "1.0", description = "Url Shortener APIs v1.0"))
class MpMonitoringApplication

fun main(args: Array<String>) {
    runApplication<MpMonitoringApplication>(*args)
}
