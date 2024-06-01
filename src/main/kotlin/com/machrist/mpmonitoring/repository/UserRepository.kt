package com.machrist.mpmonitoring.repository

import com.machrist.mpmonitoring.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByLogin(username: String): User?

    fun existsByLogin(username: String): Boolean
}
