package com.machrist.mpmonitoring.metric.storage

import com.machrist.mpmonitoring.metric.model.MetricProject
import com.machrist.mpmonitoring.metric.model.TimeSeries

interface MetricStorage {
    fun createProject(project: MetricProject)

    fun projectExists(project: MetricProject): Boolean

    fun storeMetric(project: MetricProject, timeSeries: TimeSeries)
}