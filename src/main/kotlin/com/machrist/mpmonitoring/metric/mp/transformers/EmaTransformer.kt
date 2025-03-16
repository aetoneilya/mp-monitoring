package com.machrist.mpmonitoring.metric.mp.transformers

import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint

class EmaTransformer : TimeSeriesTransformer {
    override val name: String = "exponential-moving-average"

    override fun transform(
        timeSeries: TimeSeries,
        arguments: List<String>,
    ): TimeSeries {
        val alpha: Double = arguments[0].toDouble()
        val emaValues = mutableListOf<Double>()
        var ema = timeSeries.first().value

        timeSeries.forEach { dataPoint ->
            ema = alpha * dataPoint.value + (1 - alpha) * ema
            emaValues.add(ema)
        }

        return TimeSeries(
            timeSeries.mapIndexed { index, dataPoint ->
                TimeSeriesDataPoint(
                    dateTime = dataPoint.dateTime,
                    value = emaValues[index],
                )
            },
        )
    }
}
