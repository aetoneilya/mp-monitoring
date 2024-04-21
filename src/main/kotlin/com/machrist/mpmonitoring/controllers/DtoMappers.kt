package com.machrist.mpmonitoring.controllers

import com.machrist.mpmonitoring.model.Project
import com.machrist.mpmonitoring.openapi.dto.ProjectDto

fun Project.toDto(): ProjectDto = ProjectDto(
    name = name,
    description = description ?: ""
)

fun ProjectDto.toEntity(): Project = Project(
    name = name,
    description = description
)

