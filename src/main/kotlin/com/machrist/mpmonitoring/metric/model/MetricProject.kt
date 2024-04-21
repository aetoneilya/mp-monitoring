package com.machrist.mpmonitoring.metric.model

data class MetricProject(
    val name: String,
    val sensorIds: List<String?> = emptyList()
) {
    companion object
}
