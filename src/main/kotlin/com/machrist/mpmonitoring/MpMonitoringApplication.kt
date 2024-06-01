package com.machrist.mpmonitoring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class MpMonitoringApplication

fun main(args: Array<String>) {
    runApplication<MpMonitoringApplication>(*args)
}
