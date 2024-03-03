package com.machrist.mpmonitoring.controllers

import com.machrist.mpmonitoring.api.MetricsApi
import com.machrist.mpmonitoring.common.logger
import com.machrist.mpmonitoring.metric.MetricService
import com.machrist.mpmonitoring.model.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class MetricsController(val metricService: MetricService) : MetricsApi {
    val log by logger()
    override fun getMetrics(
        project: String, service: String, getMetricsRequest: GetMetricsRequest
    ): ResponseEntity<GetMetricsResponse> {
        log.info("getMetrics: $project, $service, $getMetricsRequest")
        val metric = metricService.getMetrics(project, service, getMetricsRequest.metadata);

        return super.getMetrics(project, service, getMetricsRequest);
    }

    override fun storeMetrics(project: String, service: String, body: MetricDto): ResponseEntity<StoreMetricsResponse> {
        log.info("storeMetrics: $project, $service, $body")
        return super.storeMetrics(project, service, body)
    }

}