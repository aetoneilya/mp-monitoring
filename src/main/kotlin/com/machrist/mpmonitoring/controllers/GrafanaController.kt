package com.machrist.mpmonitoring.controllers


import com.machrist.mpmonitoring.common.logger
import com.machrist.mpmonitoring.openapi.AdHocFiltersApi
import com.machrist.mpmonitoring.openapi.GrafanaApi
import com.machrist.mpmonitoring.openapi.VariableApi
import com.machrist.mpmonitoring.openapi.dto.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class GrafanaController : GrafanaApi, AdHocFiltersApi, VariableApi {
    val log by logger()

    override fun apiEndpointsListMetricPayloadOptions(apiEndpointsListMetricPayloadOptionsRequest: ApiEndpointsListMetricPayloadOptionsRequest?): ResponseEntity<Flow<ApiEndpointsListMetricPayloadOptions200ResponseInner>> {
        log.info("apiEndpointsListMetricPayloadOptions request=$apiEndpointsListMetricPayloadOptionsRequest")

        val result = listOf(
            ApiEndpointsListMetricPayloadOptions200ResponseInner(
                value = "value",
                label = "label"
            ),
            ApiEndpointsListMetricPayloadOptions200ResponseInner(
                value = "value",
                label = "label"
            )
        )

        return ResponseEntity.ok(result.asFlow())
    }

    override fun apiEndpointsListMetrics(apiEndpointsListMetricsRequest: ApiEndpointsListMetricsRequest?): ResponseEntity<Flow<ApiEndpointsListMetrics200ResponseInner>> {
        log.info("apiEndpointsListMetrics request=$apiEndpointsListMetricsRequest")

        val result = listOf(
            ApiEndpointsListMetrics200ResponseInner(
                value = "some-metric-value",
                label = "some-metric-label",
                payloads = listOf(
                    ApiEndpointsListMetrics200ResponseInnerPayloadsInner(
                        name = "payload-name",
                        label = "payload-label",
                    ),

                    )
            ),
            ApiEndpointsListMetrics200ResponseInner(
                value = "some-metric-value-2",
                label = "some-metric-label-2",
                payloads = listOf(
                    ApiEndpointsListMetrics200ResponseInnerPayloadsInner(
                        name = "payload-name-2-1",
                        label = "payload-label-2-1",
                        type = ApiEndpointsListMetrics200ResponseInnerPayloadsInner.Type.textarea,
                        placeholder = "sosi bibu"
                    ),
                    ApiEndpointsListMetrics200ResponseInnerPayloadsInner(
                        name = "payload-name-2-2",
                        label = "payload-label-2-2",
                        type = ApiEndpointsListMetrics200ResponseInnerPayloadsInner.Type.select,
                        options = listOf(
                            ApiEndpointsListMetrics200ResponseInnerPayloadsInnerOptionsInner(
                                "asd",
                                "lable"
                            )
                        )
                    )
                )
            )
        )
        return ResponseEntity.ok(result.asFlow())
    }

    override fun apiEndpointsQuery(apiEndpointsQueryRequest: ApiEndpointsQueryRequest): ResponseEntity<Flow<ApiEndpointsQuery200ResponseInner>> {
        log.info("apiEndpointsQuery request=$apiEndpointsQueryRequest")

        val result = listOf(
            ApiEndpointsQuery200ResponseInner(
                target = "upper_25",
                datapoints = listOf(
                    listOf(BigDecimal.TEN, BigDecimal.valueOf(1450754220000)),
                    listOf(BigDecimal.ONE, BigDecimal.valueOf(1557385723416))
                )
            )
        )

        return ResponseEntity.ok(result.asFlow())
    }

    override fun apiEndpointsTagKeys(): ResponseEntity<Flow<ApiEndpointsTagKeys200ResponseInner>> {
        log.info("apiEndpointsTagKeys")

        val result = listOf(
            ApiEndpointsTagKeys200ResponseInner(
                type = "key",
                text = "text"
            ),
            ApiEndpointsTagKeys200ResponseInner(
                type = "key-2",
                text = "text-2"
            )
        )

        return ResponseEntity.ok(result.asFlow())
    }

    override fun apiEndpointsTagValues(apiEndpointsTagValuesRequest: ApiEndpointsTagValuesRequest): ResponseEntity<Flow<ApiEndpointsTagValues200ResponseInner>> {
        log.info("apiEndpointsTagValues request=$apiEndpointsTagValuesRequest")

        val result = listOf(
            ApiEndpointsTagValues200ResponseInner(
                text = "text-haha"
            ),
            ApiEndpointsTagValues200ResponseInner(
                text = "text-hahaahahha"
            )
        )

        return ResponseEntity.ok(result.asFlow())
    }


    override suspend fun apiEndpointsVariable(apiEndpointsVariableRequest: ApiEndpointsVariableRequest): ResponseEntity<Dataframe> {
        log.info("apiEndpointsVariable request=$apiEndpointsVariableRequest")
        val result = Dataframe(
            listOf(
                DataframeFieldsInner(
                    name = "test-var",
                    propertyValues = listOf("aasd", 123)
                )
            )
        )
        return ResponseEntity.ok(result)
    }
}