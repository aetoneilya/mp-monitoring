package com.machrist.mpmonitoring.metric.mp.transformers

import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint
import kotlin.math.pow
import kotlin.math.sqrt

class StandardDeviationTransformer : TimeSeriesTransformer {
    override val name: String = "Rolling Statistics"

    override fun transform(
        timeSeries: TimeSeries,
        arguments: List<String>,
    ): TimeSeries {
        val windowSize = arguments.first().toInt()
        return TimeSeries(
            timeSeries.asSequence()
                .windowed(windowSize, partialWindows = true)
                .map { window ->
                    val statistic = calculateStandardDeviation(window.map { it.value })
                    TimeSeriesDataPoint(window.last().dateTime, statistic)
                }
                .toList(),
        )
    }

    private fun calculateStandardDeviation(values: List<Double>): Double {
        val mean = values.average()
        val variance = values.map { (it - mean).pow(2) }.average()
        return sqrt(variance)
    }
}
