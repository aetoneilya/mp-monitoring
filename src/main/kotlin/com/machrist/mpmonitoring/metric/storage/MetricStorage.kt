package com.machrist.mpmonitoring.metric.storage

import com.machrist.mpmonitoring.metric.model.MetricProject

interface MetricStorage {
    fun createProject(project: MetricProject)
}