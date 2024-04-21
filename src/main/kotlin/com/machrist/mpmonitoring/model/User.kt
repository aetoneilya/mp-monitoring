package com.machrist.mpmonitoring.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import java.time.Instant

@Entity
@Table(schema = "monitoring", name = "users")
open class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_gen")
    @SequenceGenerator(name = "users_id_gen", schema = "monitoring", sequenceName = "users_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    open var id: Int?,
    @NotNull
    @Column(name = "username", nullable = false, length = Integer.MAX_VALUE)
    open var username: String?,
    @NotNull
    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant?,
    @OneToMany(mappedBy = "user")
    open var userProjectRights: MutableSet<UserProjectRight>,
)
