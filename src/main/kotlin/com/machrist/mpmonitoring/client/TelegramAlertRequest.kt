package com.machrist.mpmonitoring.client

import java.time.Instant

data class TelegramAlertRequest(
    val alertName: String,
    val from: Instant,
    val to: Instant,
    val userLogin: String,
)
