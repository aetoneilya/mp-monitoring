package com.machrist.mpmonitoring.controllers

import com.machrist.mpmonitoring.common.logger
import com.machrist.mpmonitoring.domain.MetricService
import com.machrist.mpmonitoring.metric.mp.MatrixProfileService
import com.machrist.mpmonitoring.model.Label
import com.machrist.mpmonitoring.model.Sensor
import com.machrist.mpmonitoring.openapi.AdHocFiltersApi
import com.machrist.mpmonitoring.openapi.GrafanaApi
import com.machrist.mpmonitoring.openapi.VariableApi
import com.machrist.mpmonitoring.openapi.dto.ApiEndpointsListMetricPayloadOptions200ResponseInner
import com.machrist.mpmonitoring.openapi.dto.ApiEndpointsListMetricPayloadOptionsRequest
import com.machrist.mpmonitoring.openapi.dto.ApiEndpointsListMetrics200ResponseInner
import com.machrist.mpmonitoring.openapi.dto.ApiEndpointsListMetrics200ResponseInnerPayloadsInner
import com.machrist.mpmonitoring.openapi.dto.ApiEndpointsListMetrics200ResponseInnerPayloadsInnerOptionsInner
import com.machrist.mpmonitoring.openapi.dto.ApiEndpointsListMetricsRequest
import com.machrist.mpmonitoring.openapi.dto.ApiEndpointsQuery200ResponseInner
import com.machrist.mpmonitoring.openapi.dto.ApiEndpointsQueryRequest
import com.machrist.mpmonitoring.openapi.dto.ApiEndpointsTagKeys200ResponseInner
import com.machrist.mpmonitoring.openapi.dto.ApiEndpointsTagValues200ResponseInner
import com.machrist.mpmonitoring.openapi.dto.ApiEndpointsTagValuesRequest
import com.machrist.mpmonitoring.openapi.dto.ApiEndpointsVariableRequest
import com.machrist.mpmonitoring.openapi.dto.Dataframe
import com.machrist.mpmonitoring.openapi.dto.DataframeFieldsInner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class GrafanaController(
    private val metricService: MetricService,
    private val matrixProfileService: MatrixProfileService,
) : GrafanaApi,
    AdHocFiltersApi,
    VariableApi {
    val log by logger()

    override fun apiEndpointsListMetricPayloadOptions(
        apiEndpointsListMetricPayloadOptionsRequest: ApiEndpointsListMetricPayloadOptionsRequest?,
    ): ResponseEntity<Flow<ApiEndpointsListMetricPayloadOptions200ResponseInner>> {
        log.info("apiEndpointsListMetricPayloadOptions request=$apiEndpointsListMetricPayloadOptionsRequest")

        val result =
            listOf(
                ApiEndpointsListMetricPayloadOptions200ResponseInner(
                    value = "value",
                    label = "label",
                ),
                ApiEndpointsListMetricPayloadOptions200ResponseInner(
                    value = "value",
                    label = "label",
                ),
            )

        return ResponseEntity.ok(result.asFlow())
    }

    override fun apiEndpointsListMetrics(
        apiEndpointsListMetricsRequest: ApiEndpointsListMetricsRequest?,
    ): ResponseEntity<Flow<ApiEndpointsListMetrics200ResponseInner>> {
        log.info("apiEndpointsListMetrics request=$apiEndpointsListMetricsRequest")

        val metrics = metricService.findAvailableMetrics()

        // add possibility to select multiple sensors at once
        val selected = setOf(metrics.find { it.findName() == apiEndpointsListMetricsRequest?.metric })

        val result =
            metrics.map {
                ApiEndpointsListMetrics200ResponseInner(
                    value = it.findName(),
                    payloads = mapLabelsToGrafanaModel(it.labels, selected.filterNotNull()),
                )
            }
        return ResponseEntity.ok(result.asFlow())
    }

    private fun mapLabelsToGrafanaModel(
        labels: Iterable<Label>,
        sensor: Iterable<Sensor>,
    ): List<ApiEndpointsListMetrics200ResponseInnerPayloadsInner> {
        val labelNames = labels.map { it.labelName }.filter { it != "name" }
        val possibleValuesByName = sensor.flatMap { it.buildLabelsMap().entries }.groupBy({ it.key }, { it.value })

        return labelNames.map { labelName ->
            ApiEndpointsListMetrics200ResponseInnerPayloadsInner(
                name = labelName,
                type = ApiEndpointsListMetrics200ResponseInnerPayloadsInner.Type.select,
                options =
                    possibleValuesByName[labelName]?.map {
                        ApiEndpointsListMetrics200ResponseInnerPayloadsInnerOptionsInner(it, it)
                    },
            )
        }
    }

    @Transactional
    override fun apiEndpointsQuery(
        apiEndpointsQueryRequest: ApiEndpointsQueryRequest,
    ): ResponseEntity<Flow<ApiEndpointsQuery200ResponseInner>> {
        log.info("apiEndpointsQuery request=$apiEndpointsQueryRequest")
        val from = apiEndpointsQueryRequest.range?.from
        val to = apiEndpointsQueryRequest.range?.to
        val targets = apiEndpointsQueryRequest.targets

        val sensor = metricService.findSensorsByName(targets?.first()!!.target)

        val metrics = metricService.findMetrics(listOf(sensor), from, to)

        val result =
            listOf(
                ApiEndpointsQuery200ResponseInner(
                    target = targets.first().target,
                    datapoints =
                        metrics[sensor]?.timeSeriesPoints?.map { metric ->
                            listOf(
                                BigDecimal(metric.value),
                                BigDecimal(metric.dateTime.toInstant().toEpochMilli()),
                            )
                        } ?: emptyList(),
                ),
            )

        return ResponseEntity.ok(result.asFlow())
    }

    override fun apiEndpointsTagKeys(): ResponseEntity<Flow<ApiEndpointsTagKeys200ResponseInner>> {
        log.info("apiEndpointsTagKeys")

        val result =
            listOf(
                ApiEndpointsTagKeys200ResponseInner(
                    type = "key",
                    text = "text",
                ),
                ApiEndpointsTagKeys200ResponseInner(
                    type = "key-2",
                    text = "text-2",
                ),
            )

        return ResponseEntity.ok(result.asFlow())
    }

    override fun apiEndpointsTagValues(
        apiEndpointsTagValuesRequest: ApiEndpointsTagValuesRequest,
    ): ResponseEntity<Flow<ApiEndpointsTagValues200ResponseInner>> {
        log.info("apiEndpointsTagValues request=$apiEndpointsTagValuesRequest")

        val result =
            listOf(
                ApiEndpointsTagValues200ResponseInner(
                    text = "text-haha",
                ),
                ApiEndpointsTagValues200ResponseInner(
                    text = "text-hahaahahha",
                ),
            )

        return ResponseEntity.ok(result.asFlow())
    }

    override suspend fun apiEndpointsVariable(apiEndpointsVariableRequest: ApiEndpointsVariableRequest): ResponseEntity<Dataframe> {
        log.info("apiEndpointsVariable request=$apiEndpointsVariableRequest")
        val result =
            Dataframe(
                listOf(
                    DataframeFieldsInner(
                        name = "test-var",
                        propertyValues = listOf("aasd", 123),
                    ),
                ),
            )
        return ResponseEntity.ok(result)
    }
}
