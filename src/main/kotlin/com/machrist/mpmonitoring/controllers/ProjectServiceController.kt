package com.machrist.mpmonitoring.controllers

import com.machrist.mpmonitoring.api.ProjectApi
import com.machrist.mpmonitoring.common.logger
import com.machrist.mpmonitoring.model.ProjectDto
import com.machrist.mpmonitoring.model.ServiceDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectServiceController: ProjectApi {
    val log by logger()
    override fun createProject(projectDto: ProjectDto): ResponseEntity<ProjectDto> {
        log.info("createProject: $projectDto")
        return super.createProject(projectDto)
    }

    override fun createService(project: String, serviceDto: ServiceDto): ResponseEntity<ServiceDto> {
        log.info("createService: $project, $serviceDto")
        return super.createService(project, serviceDto)
    }

    override fun getProject(name: String): ResponseEntity<ProjectDto> {
        log.info("getProject: $name")
        return super.getProject(name)
    }

    override fun getService(project: String, name: String): ResponseEntity<ServiceDto> {
        log.info("getService: $project, $name")
        return super.getService(project, name)
    }
}