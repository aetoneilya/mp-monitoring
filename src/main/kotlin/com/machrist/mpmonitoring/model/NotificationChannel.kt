package com.machrist.mpmonitoring.model

data class NotificationChannel(
    val id: Int,
    val type: ChannelType,
    val address: Map<String, String>
)
