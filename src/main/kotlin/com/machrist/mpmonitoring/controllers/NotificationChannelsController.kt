package com.machrist.mpmonitoring.controllers

import com.machrist.mpmonitoring.openapi.NotificationChannelsApi
import com.machrist.mpmonitoring.openapi.dto.CreateNotificationChannelRequest
import com.machrist.mpmonitoring.openapi.dto.NotificationChannel
import com.machrist.mpmonitoring.openapi.dto.UpdateNotificationChannelRequest
import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity

class NotificationChannelsController : NotificationChannelsApi {
    override suspend fun createNotificationChannel(
        createNotificationChannelRequest: CreateNotificationChannelRequest,
    ): ResponseEntity<NotificationChannel> {
        return super.createNotificationChannel(createNotificationChannelRequest)
    }

    override suspend fun deleteNotificationChannel(channelId: Int): ResponseEntity<Unit> {
        return super.deleteNotificationChannel(channelId)
    }

    override suspend fun getNotificationChannel(channelId: Int): ResponseEntity<NotificationChannel> {
        return super.getNotificationChannel(channelId)
    }

    override fun listNotificationChannels(): ResponseEntity<Flow<NotificationChannel>> {
        return super.listNotificationChannels()
    }

    override suspend fun updateNotificationChannel(
        channelId: Int,
        updateNotificationChannelRequest: UpdateNotificationChannelRequest,
    ): ResponseEntity<NotificationChannel> {
        return super.updateNotificationChannel(channelId, updateNotificationChannelRequest)
    }
}
