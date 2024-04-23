package com.machrist.mpmonitoring.metric.model

data class TimeSeries(
    val timeSeriesPoints: List<TimeSeriesDataPoint> = emptyList(),
) {
    fun size() = timeSeriesPoints.size.toLong()
}
