package com.machrist.mpmonitoring.metric.model

data class Sensor(
    val id: String,
    val labels: Map<String, String>
)
