package com.machrist.mpmonitoring.metric

import com.machrist.mpmonitoring.metric.model.MetricProject
import com.machrist.mpmonitoring.metric.model.from
import com.machrist.mpmonitoring.metric.storage.MetricStorage
import com.machrist.mpmonitoring.model.Project
import com.machrist.mpmonitoring.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(
    val metricStorage: MetricStorage,
    val projectRepository: ProjectRepository
) {
    fun getProject(name: String): Project? = projectRepository.findByName(name)

    fun createProject(project: Project): Project {
        val metricProject = MetricProject.from(project)
        if (!metricStorage.projectExists(metricProject)) {
            metricStorage.createProject(metricProject)
        }
        return projectRepository.findByName(project.name) ?: projectRepository.save(project)
    }
}

