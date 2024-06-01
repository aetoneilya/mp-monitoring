package com.machrist.mpmonitoring.repository

import com.machrist.mpmonitoring.model.Label
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LabelRepository : JpaRepository<Label, Int> {
    fun findByLabelNameAndLabelValue(
        labelName: String,
        labelValue: String,
    ): List<Label>

    fun findByLabelNameIsInAndLabelValueIsIn(
        labelName: Collection<String>,
        labelValue: Collection<String>,
    ): List<Label>

    fun findLabelByLabelNameAndLabelValue(
        labelName: String,
        labelValue: String
    ) : Label?
}
