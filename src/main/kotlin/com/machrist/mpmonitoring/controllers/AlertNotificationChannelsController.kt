package com.machrist.mpmonitoring.controllers

import com.machrist.mpmonitoring.openapi.AlertNotificationChannelsApi
import com.machrist.mpmonitoring.openapi.dto.AddNotificationChannelToAlertRequest
import org.springframework.http.ResponseEntity

class AlertNotificationChannelsController : AlertNotificationChannelsApi {
    override suspend fun addNotificationChannelToAlert(
        projectId: Int,
        alertId: Int,
        addNotificationChannelToAlertRequest: AddNotificationChannelToAlertRequest,
    ): ResponseEntity<Unit> {
        return super.addNotificationChannelToAlert(projectId, alertId, addNotificationChannelToAlertRequest)
    }

    override suspend fun removeNotificationChannelFromAlert(
        projectId: Int,
        alertId: Int,
        channelId: Int,
    ): ResponseEntity<Unit> {
        return super.removeNotificationChannelFromAlert(projectId, alertId, channelId)
    }
}
