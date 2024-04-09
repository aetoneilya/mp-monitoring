package com.machrist.mpmonitoring.controllers

import com.machrist.mpmonitoring.api.MetricsApi
import com.machrist.mpmonitoring.common.logger
import com.machrist.mpmonitoring.common.toOffsetDateTime
import com.machrist.mpmonitoring.metric.MetricService
import com.machrist.mpmonitoring.metric.model.MetricProject
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint
import com.machrist.mpmonitoring.model.GetMetricsRequest
import com.machrist.mpmonitoring.model.GetMetricsResponse
import com.machrist.mpmonitoring.model.StoreMetricsRequest
import com.machrist.mpmonitoring.model.StoreMetricsResponse
import com.machrist.mpmonitoring.model.StoreMetricsResponse.Status
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class MetricsController(val metricService: MetricService) : MetricsApi {
    val log by logger()
    override suspend fun getMetrics(
        project: String, getMetricsRequest: GetMetricsRequest
    ): ResponseEntity<GetMetricsResponse> {
        log.info("getMetrics: $project, $getMetricsRequest")

        return super.getMetrics(project, getMetricsRequest);
    }

    override suspend fun storeMetrics(
        project: String,
        storeMetricsRequest: StoreMetricsRequest
    ): ResponseEntity<StoreMetricsResponse> {
        log.info("storeMetrics: $project, $storeMetricsRequest")
        // check if project exist

        metricService.storeMetrics(
            MetricProject(project),
            storeMetricsRequest.metrics?.metadata ?: emptyMap(),
            storeMetricsRequest.metrics?.timeSeries?.map {
                TimeSeriesDataPoint(
                    toOffsetDateTime(it.timestamp),
                    it.value.toDouble()
                )
            } ?: emptyList()
        )

        return ResponseEntity.ok(StoreMetricsResponse(Status.successful, 1))
    }
}