package com.machrist.mpmonitoring.controllers

import com.machrist.mpmonitoring.api.ProjectApi
import com.machrist.mpmonitoring.common.logger
import com.machrist.mpmonitoring.metric.ProjectService
import com.machrist.mpmonitoring.metric.model.MetricProject
import com.machrist.mpmonitoring.model.ProjectDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectController(val projectService: ProjectService) : ProjectApi {
    val log by logger()
    override suspend fun createProject(projectDto: ProjectDto): ResponseEntity<ProjectDto> {
        log.info("createProject: $projectDto")
        projectService.createProject(MetricProject(projectDto.name))
        return ResponseEntity.ok(ProjectDto(projectDto.name, "null"))
    }

    override suspend fun getProject(name: String): ResponseEntity<ProjectDto> {
        log.info("getProject: $name")
        return super.getProject(name)
    }
}