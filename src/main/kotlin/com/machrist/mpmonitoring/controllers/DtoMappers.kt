package com.machrist.mpmonitoring.controllers

import com.machrist.mpmonitoring.common.toOffsetDateTime
import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint
import com.machrist.mpmonitoring.model.Project
import com.machrist.mpmonitoring.openapi.dto.ProjectDto
import com.machrist.mpmonitoring.openapi.dto.TimeSeriesDataPointDto

fun Project.toDto(): ProjectDto =
    ProjectDto(
        name = name,
        description = description ?: "",
    )

fun ProjectDto.toEntity(): Project =
    Project(
        name = name,
        description = description,
    )

fun List<TimeSeriesDataPointDto>?.toEntity() =
    TimeSeries(
        this?.map { TimeSeriesDataPoint(toOffsetDateTime(it.timestamp), it.value.toDouble()) }
            ?.sortedBy { it.dateTime }
            .orEmpty(),
    )

fun TimeSeries.toDto(): List<TimeSeriesDataPointDto> {
    return this.map {
        TimeSeriesDataPointDto(
            it.dateTime.toInstant().epochSecond,
            it.value.toBigDecimal(),
        )
    }
}
