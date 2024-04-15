package com.machrist.mpmonitoring.repository.converter

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter


@Converter(autoApply = true)
class JsonToMapConverter : AttributeConverter<Map<String, Any>, String> {

    private val objectMapper = ObjectMapper()

    override fun convertToDatabaseColumn(attribute: Map<String, Any>?): String? {
        return attribute?.let { objectMapper.writeValueAsString(it) }
    }

    override fun convertToEntityAttribute(dbData: String?): Map<String, Any>? {
        return dbData?.let {
            objectMapper.readValue(it, object : TypeReference<Map<String, Any>>() {})
        }
    }
}
