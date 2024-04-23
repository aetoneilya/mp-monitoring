package com.machrist.mpmonitoring.controllers

import com.machrist.mpmonitoring.common.logger
import com.machrist.mpmonitoring.common.toOffsetDateTime
import com.machrist.mpmonitoring.metric.MetricService
import com.machrist.mpmonitoring.metric.ProjectService
import com.machrist.mpmonitoring.openapi.MetricsApi
import com.machrist.mpmonitoring.openapi.dto.GetMetricsRequest
import com.machrist.mpmonitoring.openapi.dto.GetMetricsResponse
import com.machrist.mpmonitoring.openapi.dto.StoreMetricsRequest
import com.machrist.mpmonitoring.openapi.dto.StoreMetricsResponse
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestController
class MetricsController(
    val metricService: MetricService,
    val projectService: ProjectService,
) : MetricsApi {
    val log by logger()

    override suspend fun findMetrics(
        projectName: String,
        getMetricsRequest: GetMetricsRequest,
    ): ResponseEntity<GetMetricsResponse> =
        with(getMetricsRequest) {
            val project = findProjectOrThrow(projectName)

            val sensor = metricService.findSensorsByLabels(metadata)
            metricService.findMetrics(sensor, from?.let { toOffsetDateTime(it) }, to?.let { toOffsetDateTime(it) })

            return ResponseEntity.ok(GetMetricsResponse())
        }

    override suspend fun getMetrics(
        projectName: String,
        getMetricsRequest: GetMetricsRequest,
    ): ResponseEntity<GetMetricsResponse> {
        log.info("getMetrics: $projectName, $getMetricsRequest")

        return super.getMetrics(projectName, getMetricsRequest)
    }

    override suspend fun storeMetrics(
        projectName: String,
        storeMetricsRequest: StoreMetricsRequest,
    ): ResponseEntity<StoreMetricsResponse> {
        log.info("storeMetrics: $projectName, $storeMetricsRequest")

        val project = findProjectOrThrow(projectName)

        val metadata = storeMetricsRequest.metrics.metadata
        val sensor =
            metricService.findSensorsByLabels(metadata).minByOrNull { it.labels.count() }
                ?: metricService.createSensor(project, metadata)

        log.info("found sensor: $sensor")

        val (_, count) =
            metricService.storeMetrics(
                project,
                sensor,
                storeMetricsRequest.metrics.timeSeries.toDto(),
            )

        return ResponseEntity.ok(StoreMetricsResponse(StoreMetricsResponse.Status.successful, count.toInt()))
    }

    private fun findProjectOrThrow(projectName: String) =
        projectService.getProject(projectName)
            ?: throw NoResourceFoundException(HttpMethod.POST, "project/$projectName")
}
