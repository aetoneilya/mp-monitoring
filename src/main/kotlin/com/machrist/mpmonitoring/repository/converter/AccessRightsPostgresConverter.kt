package com.machrist.mpmonitoring.repository.converter

import com.machrist.mpmonitoring.model.AccessRights
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.util.*

@Converter(autoApply = true)
class AccessRightsPostgresConverter : AttributeConverter<AccessRights, String> {
    override fun convertToDatabaseColumn(attribute: AccessRights?): String {
        return attribute.toString()
    }

    override fun convertToEntityAttribute(dbData: String?): AccessRights {
        return AccessRights.valueOf(dbData!!.uppercase(Locale.getDefault()))
    }
}