package com.machrist.mpmonitoring.metric.mp.transformers

import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint

class MovingAverageTransformer : Transformer {
    override val name: String = "min"

    override fun transform(
        timeSeries: TimeSeries,
        arguments: List<String>,
    ): TimeSeries {
        val sum = timeSeries.sumOf { it.value }
        val count = timeSeries.count()
        val average = sum / count

        return TimeSeries(
            listOf(
                TimeSeriesDataPoint(timeSeries.first().dateTime, average),
                TimeSeriesDataPoint(timeSeries.last().dateTime, average),
            ),
        )
    }
}
