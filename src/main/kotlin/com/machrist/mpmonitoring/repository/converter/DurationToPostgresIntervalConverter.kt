package com.machrist.mpmonitoring.repository.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.time.Duration

@Converter(autoApply = true)
class DurationToPostgresIntervalConverter : AttributeConverter<Duration, String> {

    override fun convertToDatabaseColumn(attribute: Duration?): String? {
        return attribute?.let { "interval '${it.seconds} seconds'" }
    }

    override fun convertToEntityAttribute(dbData: String?): Duration? {
        return dbData?.let {
            val seconds = it.replace("interval", "").replace("seconds", "").trim().toLong()
            Duration.ofSeconds(seconds)
        }
    }
}
