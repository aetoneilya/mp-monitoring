package com.machrist.mpmonitoring.controllers

import com.machrist.mpmonitoring.common.logger
import com.machrist.mpmonitoring.metric.storage.MetricStorage
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(val metricStorage: MetricStorage) {
    val log by logger()
    @GetMapping("hello")
    private fun test(): String {
//        metricStorage.createProject(MetricProject("name"))
        return "successful"
    }
}