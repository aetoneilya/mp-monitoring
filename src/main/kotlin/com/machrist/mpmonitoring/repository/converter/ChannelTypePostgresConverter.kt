package com.machrist.mpmonitoring.repository.converter

import com.machrist.mpmonitoring.model.ChannelType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.util.*

@Converter(autoApply = true)
class ChannelTypePostgresConverter : AttributeConverter<ChannelType, String> {
    override fun convertToDatabaseColumn(attribute: ChannelType?): String {
        return attribute.toString()
    }

    override fun convertToEntityAttribute(dbData: String?): ChannelType {
        return ChannelType.valueOf(dbData!!.uppercase(Locale.getDefault()))
    }
}