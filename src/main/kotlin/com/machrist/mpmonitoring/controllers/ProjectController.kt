package com.machrist.mpmonitoring.controllers


import com.machrist.mpmonitoring.common.logger
import com.machrist.mpmonitoring.metric.ProjectService
import com.machrist.mpmonitoring.openapi.ProjectApi
import com.machrist.mpmonitoring.openapi.dto.ProjectDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectController(val projectService: ProjectService) : ProjectApi {
    val log by logger()

    override suspend fun createProject(projectDto: ProjectDto): ResponseEntity<ProjectDto> {
        log.info("createProject: $projectDto")
        val project = projectService.createProject(projectDto.toEntity())
        return ResponseEntity.ok(project.toDto())
    }

    override suspend fun getProject(projectName: String): ResponseEntity<ProjectDto> {
        log.info("getProject: $projectName")
        return projectService.getProject(projectName)?.toDto()
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }
}