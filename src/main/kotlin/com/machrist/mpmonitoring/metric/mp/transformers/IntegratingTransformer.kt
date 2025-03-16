package com.machrist.mpmonitoring.metric.mp.transformers

import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint

class IntegratingTransformer : TimeSeriesTransformer {
    override val name: String = "integration"

    override fun transform(
        timeSeries: TimeSeries,
        arguments: List<String>,
    ): TimeSeries {
        var integral = 0.0
        return TimeSeries(
            timeSeries.map { dataPoint ->
                integral += dataPoint.value
                TimeSeriesDataPoint(
                    dateTime = dataPoint.dateTime,
                    value = integral,
                )
            },
        )
    }
}
