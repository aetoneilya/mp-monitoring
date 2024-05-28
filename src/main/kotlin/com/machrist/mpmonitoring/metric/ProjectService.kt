package com.machrist.mpmonitoring.metric

import com.machrist.mpmonitoring.metric.storage.MetricStorage
import com.machrist.mpmonitoring.model.Project
import com.machrist.mpmonitoring.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(
    val metricStorage: MetricStorage,
    val projectRepository: ProjectRepository,
) {
    fun getProject(name: String): Project? = projectRepository.findByName(name)

    fun createProject(project: Project): Project {
        metricStorage.createProject(project.name)

        return projectRepository.findByName(project.name) ?: projectRepository.save(project)
    }
}
