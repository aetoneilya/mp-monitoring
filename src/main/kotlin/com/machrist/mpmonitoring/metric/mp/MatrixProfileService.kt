package com.machrist.mpmonitoring.metric.mp

import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint
import com.machrist.mpmonitoring.metric.mp.matrixprofile.Stomp
import org.springframework.stereotype.Service

@Service
class MatrixProfileService {
    fun stomp(timeSeries: TimeSeries): TimeSeries {
        val stomp = Stomp(48, 10320)
        timeSeries.timeSeriesPoints.asSequence()
            .map { it.value }
            .forEach(stomp::update)

        val mp = stomp.get()?.profile
        return TimeSeries(
            timeSeries.timeSeriesPoints
                .mapIndexed { index, timeSeriesDataPoint ->
                    TimeSeriesDataPoint(
                        dateTime = timeSeriesDataPoint.dateTime,
                        value = mp?.getOrNull(index) ?: 0.0
                    )
                },
        )
    }
}
