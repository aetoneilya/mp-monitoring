package com.machrist.mpmonitoring.metric

import com.machrist.mpmonitoring.metric.model.MetricProject
import com.machrist.mpmonitoring.metric.storage.MetricStorage
import com.machrist.mpmonitoring.model.Project
import com.machrist.mpmonitoring.repository.ProjectRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProjectService(
    val metricStorage: MetricStorage,
    val projectRepository: ProjectRepository
) {
    fun getProjects(): List<MetricProject> {
        return emptyList()
    }

    fun createProject(project: MetricProject) {
        metricStorage.createProject(project.name)
        projectRepository.save(Project(1, "Name", "Description", LocalDateTime.now()))
    }
}