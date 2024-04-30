package com.machrist.mpmonitoring.metric.storage

import com.machrist.mpmonitoring.metric.model.TimeSeries
import java.time.OffsetDateTime

interface MetricStorage {
    fun createProject(projectName: String)

    fun projectExists(projectName: String): Boolean

    fun storeMetric(
        projectName: String,
        sensorName: String,
        timeSeries: TimeSeries,
    )

    fun getMetric(
        projectName: String,
        sensorName: String,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
    ): TimeSeries
}
