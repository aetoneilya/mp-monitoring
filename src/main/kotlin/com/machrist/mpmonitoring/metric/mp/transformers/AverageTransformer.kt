package com.machrist.mpmonitoring.metric.mp.transformers

import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint

class AverageTransformer : TimeSeriesTransformer {
    override val name: String = "average"

    override fun transform(
        timeSeries: TimeSeries,
        arguments: List<String>,
    ): TimeSeries {
        val average = timeSeries.map { it.value }.average()

        return TimeSeries(
            listOf(
                TimeSeriesDataPoint(timeSeries.first().dateTime, average),
                TimeSeriesDataPoint(timeSeries.last().dateTime, average),
            ),
        )
    }
}
