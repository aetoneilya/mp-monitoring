package com.machrist.mpmonitoring.metric.mp.transformers

import com.machrist.mpmonitoring.metric.model.TimeSeries

interface TimeSeriesTransformer {
    val name: String

    fun transform(
        timeSeries: TimeSeries,
        arguments: List<String>,
    ): TimeSeries
}
