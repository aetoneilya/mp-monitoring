package com.machrist.mpmonitoring.metric.model

data class MetricProject(
    val name: String,
    val sensors: List<Sensor>? = emptyList()
)
