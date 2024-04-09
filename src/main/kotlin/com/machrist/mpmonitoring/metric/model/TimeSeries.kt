package com.machrist.mpmonitoring.metric.model

data class TimeSeries(
    val sensor: Sensor,
    val timeSeriesPoints: List<TimeSeriesDataPoint>? = emptyList()
)