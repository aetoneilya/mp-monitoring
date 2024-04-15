package com.machrist.mpmonitoring.controllers


import com.machrist.mpmonitoring.openapi.AlertsApi
import com.machrist.mpmonitoring.openapi.dto.Alert
import com.machrist.mpmonitoring.openapi.dto.CreateAlertRequest
import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity

class AlertsController: AlertsApi {
    override suspend fun createAlert(projectId: Int, createAlertRequest: CreateAlertRequest): ResponseEntity<Alert> {
        return super.createAlert(projectId, createAlertRequest)
    }

    override suspend fun deleteAlert(projectId: Int, alertId: Int): ResponseEntity<Unit> {
        return super.deleteAlert(projectId, alertId)
    }

    override suspend fun getAlert(projectId: Int, alertId: Int): ResponseEntity<Alert> {
        return super.getAlert(projectId, alertId)
    }

    override fun listAlerts(projectId: Int): ResponseEntity<Flow<Alert>> {
        return super.listAlerts(projectId)
    }
}