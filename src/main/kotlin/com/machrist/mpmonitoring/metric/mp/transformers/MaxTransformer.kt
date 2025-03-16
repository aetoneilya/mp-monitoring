package com.machrist.mpmonitoring.metric.mp.transformers

import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint

class MaxTransformer : TimeSeriesTransformer {
    override val name: String = "max"

    override fun transform(
        timeSeries: TimeSeries,
        arguments: List<String>,
    ): TimeSeries {
        val max = timeSeries.asSequence().maxBy { it.value }

        return TimeSeries(
            listOf(
                TimeSeriesDataPoint(timeSeries.first().dateTime, max.value),
                TimeSeriesDataPoint(timeSeries.last().dateTime, max.value),
            ),
        )
    }
}
