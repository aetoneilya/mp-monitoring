package com.machrist.mpmonitoring.metric.mp.transformers

import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint
import kotlin.math.pow
import kotlin.math.sqrt

class ZScoreNormalizationTransformer : TimeSeriesTransformer {
    override val name: String = "z-score-normalization"

    override fun transform(
        timeSeries: TimeSeries,
        arguments: List<String>,
    ): TimeSeries {
        val values = timeSeries.map { it.value }
        val mean = values.average()
        val stdDev = calculateStandardDeviation(values, mean)

        return TimeSeries(
            timeSeries.map { dataPoint ->
                TimeSeriesDataPoint(
                    dateTime = dataPoint.dateTime,
                    value = (dataPoint.value - mean) / stdDev,
                )
            },
        )
    }

    private fun calculateStandardDeviation(
        values: List<Double>,
        mean: Double,
    ): Double {
        val variance = values.map { (it - mean).pow(2) }.average()
        return sqrt(variance)
    }
}
