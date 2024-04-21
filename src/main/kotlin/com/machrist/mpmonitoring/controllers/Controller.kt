package com.machrist.mpmonitoring.controllers

import com.machrist.mpmonitoring.common.logger
import com.machrist.mpmonitoring.metric.MetricService
import com.machrist.mpmonitoring.metric.storage.MetricStorage
import com.machrist.mpmonitoring.repository.LabelRepository
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(
    val metricStorage: MetricStorage,
    val metricService: MetricService,
    val labelRepository: LabelRepository
) {
    val log by logger()

//    @PostMapping("hello")
//    private fun test(@RequestBody metadata: Map<String, String>): String {
//        log.info(metadata.toString())
//        return labelRepository.findByLabelNameAndLabelValuePairs(metadata.map { it }.toList()).toString()
//    }
}