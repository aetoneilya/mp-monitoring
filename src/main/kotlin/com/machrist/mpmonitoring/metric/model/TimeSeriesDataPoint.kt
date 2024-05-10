package com.machrist.mpmonitoring.metric.model

import java.time.OffsetDateTime

data class TimeSeriesDataPoint(
    val dateTime: OffsetDateTime,
    val value: Double
)
