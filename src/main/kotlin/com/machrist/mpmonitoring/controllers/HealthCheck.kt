package com.machrist.mpmonitoring.controllers


import com.machrist.mpmonitoring.openapi.HealthCheckApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheck : HealthCheckApi {
    override suspend fun apiEndpointsDatasourceHealth(): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }
}