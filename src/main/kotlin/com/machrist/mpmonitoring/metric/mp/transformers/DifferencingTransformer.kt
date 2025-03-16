package com.machrist.mpmonitoring.metric.mp.transformers

import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint

class DifferencingTransformer : TimeSeriesTransformer {
    override val name: String = "differencing"

    override fun transform(
        timeSeries: TimeSeries,
        arguments: List<String>,
    ): TimeSeries {
        val order = arguments[0].toInt()
        var differencedSeries = timeSeries
        repeat(order) {
            differencedSeries = calculateDifference(differencedSeries)
        }
        return differencedSeries
    }

    private fun calculateDifference(timeSeries: TimeSeries): TimeSeries {
        return TimeSeries(
            timeSeries.dropLast(1).mapIndexed { index, dataPoint ->
                TimeSeriesDataPoint(
                    dateTime = timeSeries[index + 1].dateTime,
                    value = timeSeries[index + 1].value - dataPoint.value,
                )
            },
        )
    }
}
