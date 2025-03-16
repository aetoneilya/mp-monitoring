package com.machrist.mpmonitoring.metric.mp.transformers

import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint

class MinTransformer : TimeSeriesTransformer {
    override val name: String = "min"

    override fun transform(
        timeSeries: TimeSeries,
        arguments: List<String>,
    ): TimeSeries {
        val min = timeSeries.asSequence().minBy { it.value }

        return TimeSeries(
            listOf(
                TimeSeriesDataPoint(timeSeries.first().dateTime, min.value),
                TimeSeriesDataPoint(timeSeries.last().dateTime, min.value),
            ),
        )
    }
}
