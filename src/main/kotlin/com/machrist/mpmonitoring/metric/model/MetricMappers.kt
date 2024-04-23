package com.machrist.mpmonitoring.metric.model

import com.machrist.mpmonitoring.model.Project

fun MetricProject.Companion.from(project: Project): MetricProject =
    MetricProject(project.name, project.sensors.map { it.storageSensorName }.toList())
