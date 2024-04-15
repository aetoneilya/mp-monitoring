package com.machrist.mpmonitoring.model

import java.time.Instant

data class User(
    val id: Int,
    val username: String,
    val createdAt: Instant
)
