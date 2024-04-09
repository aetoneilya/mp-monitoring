package com.machrist.mpmonitoring.controllers

import com.machrist.mpmonitoring.api.HealthCheckApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class HealtCheck : HealthCheckApi {
    override suspend fun apiEndpointsDatasourceHealth(): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }
}