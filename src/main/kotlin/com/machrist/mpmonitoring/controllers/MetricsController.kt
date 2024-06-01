package com.machrist.mpmonitoring.controllers

import com.machrist.mpmonitoring.common.logger
import com.machrist.mpmonitoring.common.toOffsetDateTime
import com.machrist.mpmonitoring.domain.MetricService
import com.machrist.mpmonitoring.domain.ProjectService
import com.machrist.mpmonitoring.openapi.MetricsApi
import com.machrist.mpmonitoring.openapi.dto.GetMetricsRequest
import com.machrist.mpmonitoring.openapi.dto.GetMetricsResponse
import com.machrist.mpmonitoring.openapi.dto.MetricDto
import com.machrist.mpmonitoring.openapi.dto.StoreMetricsRequest
import com.machrist.mpmonitoring.openapi.dto.StoreMetricsResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.resource.NoResourceFoundException

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
            val sensors =
                metricService.findSensorsByLabels(metadata) ?: return ResponseEntity.ok(
                    GetMetricsResponse
                        (emptyList()),
                )

            val timeSeriesBySensor = metricService.findMetrics(sensors, toOffsetDateTime(from), toOffsetDateTime(to))

            val metricsDto =
                timeSeriesBySensor.map { (sensor, timeSeries) ->
                    MetricDto(
                        sensor.buildLabelsMap(),
                        timeSeries.toDto(),
                    )
                }

            return ResponseEntity.ok(GetMetricsResponse(metricsDto))
        }

    override suspend fun getMetrics(projectName: String): ResponseEntity<GetMetricsResponse> {
        return super.getMetrics(projectName)
    }

    override suspend fun storeMetrics(
        projectName: String,
        storeMetricsRequest: StoreMetricsRequest,
    ): ResponseEntity<StoreMetricsResponse> {
        log.info("storeMetrics: $projectName, $storeMetricsRequest")

        val project = findProjectOrThrow(projectName)

        val metadata = storeMetricsRequest.metrics.metadata
        val sensor =
            metricService.findSensorsByLabels(metadata)?.minByOrNull { it.labels.count() }
                ?: metricService.createMetric(project, metadata)

        log.info("found sensor: $sensor")

        log.info("ts: ${storeMetricsRequest.metrics.timeSeries.toEntity()}")

        val (_, count) =
            metricService.storeMetrics(
                project,
                sensor,
                storeMetricsRequest.metrics.timeSeries.toEntity(),
            )

        return ResponseEntity.ok(StoreMetricsResponse(StoreMetricsResponse.Status.successful, count.toInt()))
    }

    private fun findProjectOrThrow(projectName: String) =
        projectService.getProject(projectName)
            ?: throw NoResourceFoundException("project/$projectName")
}
