package com.machrist.mpmonitoring.metric.model

data class TimeSeries(
    val sensorId: String,
    val timeSeriesPoints: List<TimeSeriesDataPoint> = emptyList()
)