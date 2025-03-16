package com.machrist.mpmonitoring.metric.model

data class TimeSeries(
    private val timeSeriesPoints: List<TimeSeriesDataPoint> = emptyList(),
) : List<TimeSeriesDataPoint> by timeSeriesPoints
