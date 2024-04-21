package com.machrist.mpmonitoring.repository


import com.machrist.mpmonitoring.model.Label
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LabelRepository : JpaRepository<Label, Int> {
    fun findByLabelNameAndLabelValue(labelName: String, labelValue: String): List<Label>
}