package com.machrist.mpmonitoring.metric

import com.machrist.mpmonitoring.metric.model.MetricProject
import com.machrist.mpmonitoring.metric.storage.MetricStorage
import org.springframework.stereotype.Service

@Service
class ProjectService(
    val metricStorage: MetricStorage
) {
    fun getProjects(): List<MetricProject> {
        return emptyList()
    }

    fun createProject(project: MetricProject) {
        metricStorage.createProject(project.name)
    }
}