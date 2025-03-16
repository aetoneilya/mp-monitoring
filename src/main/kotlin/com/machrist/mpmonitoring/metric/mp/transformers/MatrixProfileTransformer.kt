package com.machrist.mpmonitoring.metric.mp.transformers

import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint
import com.machrist.mpmonitoring.metric.mp.matrixprofile.Stomp

class MatrixProfileTransformer : TimeSeriesTransformer {
    override val name: String = "matrix-profile"

    override fun transform(
        timeSeries: TimeSeries,
        arguments: List<String>,
    ): TimeSeries {
        if (arguments.isEmpty()) {
            throw IllegalArgumentException("Argument list must contain at least one integer argument window size")
        }
        val windowSize = arguments[0].toInt()
        return calculateMatrixProfile(timeSeries, windowSize)
    }

    private fun calculateMatrixProfile(
        timeSeries: TimeSeries,
        windowSize: Int,
    ): TimeSeries {
        val stomp = Stomp(windowSize, timeSeries.size)
        timeSeries.asSequence()
            .map { it.value }
            .forEach(stomp::update)

        val mp = stomp.get()!!.profile
        return TimeSeries(
            timeSeries
                .mapIndexed { index, timeSeriesDataPoint ->
                    TimeSeriesDataPoint(
                        dateTime = timeSeriesDataPoint.dateTime,
                        value = mp.getOrNull(index) ?: 0.0,
                    )
                },
        )
    }
}
