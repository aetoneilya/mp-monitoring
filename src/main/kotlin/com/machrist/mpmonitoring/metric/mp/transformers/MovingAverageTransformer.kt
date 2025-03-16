package com.machrist.mpmonitoring.metric.mp.transformers

import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint

class MovingAverageTransformer : TimeSeriesTransformer {
    override val name: String = "moving-average"

    override fun transform(
        timeSeries: TimeSeries,
        arguments: List<String>,
    ): TimeSeries {
        if (arguments.isEmpty()) {
            throw IllegalArgumentException("Argument list must contain at least one integer argument window size")
        }
        val windowSize = arguments[0].toInt()

        return movingAverage(timeSeries, windowSize)
    }

    private fun movingAverage(
        timeSeries: TimeSeries,
        windowSize: Int,
    ): TimeSeries {
        val movingAverages =
            timeSeries.asSequence()
                .map { it.value }
                .windowed(size = windowSize, step = 1, partialWindows = true)
                .map { window -> window.average() }
                .toList()

        return TimeSeries(
            timeSeries.mapIndexed { index, dataPoint ->
                TimeSeriesDataPoint(
                    dateTime = dataPoint.dateTime,
                    value = movingAverages.getOrNull(index) ?: 0.0,
                )
            },
        )
    }
}
