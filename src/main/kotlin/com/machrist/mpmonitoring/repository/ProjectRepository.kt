package com.machrist.mpmonitoring.repository

import com.machrist.mpmonitoring.model.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository : JpaRepository<Project, Int> {
    fun findByName(status: String): Project?
}
