package com.machrist.mpmonitoring.metric.storage

import com.machrist.mpmonitoring.metric.model.TimeSeries

interface MetricStorage {
    fun createProject(projectName: String)

    fun storeMetric(projectName: String, timeSeries: TimeSeries)
}