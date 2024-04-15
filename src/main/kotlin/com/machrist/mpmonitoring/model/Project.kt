package com.machrist.mpmonitoring.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "projects", schema = "monitoring")
data class Project(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = true)
    var description: String?,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime
)