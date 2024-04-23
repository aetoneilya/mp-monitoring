package com.machrist.mpmonitoring.metric.storage

import com.machrist.mpmonitoring.metric.model.MetricProject
import com.machrist.mpmonitoring.metric.model.TimeSeries
import java.time.OffsetDateTime

interface MetricStorage {
    fun createProject(project: MetricProject)

    fun projectExists(project: MetricProject): Boolean

    fun storeMetric(
        project: MetricProject,
        sensorName: String,
        timeSeries: TimeSeries,
    )

    fun getMetric(
        project: MetricProject,
        sensorName: String,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
    ): TimeSeries
}
