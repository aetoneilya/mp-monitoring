package com.machrist.mpmonitoring.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

fun <T : Any> T.logger(): Lazy<Logger> = lazyOf(LoggerFactory.getLogger(this.javaClass.name))

fun toOffsetDateTime(epochMillis: Long): OffsetDateTime {
    return OffsetDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), ZoneOffset.UTC)
}