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
import org.hibernate.annotations.ColumnDefault
import java.time.Instant

@Entity
@Table(schema = "monitoring", name = "projects")
open class Project(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projects_id_gen")
    @SequenceGenerator(
        name = "projects_id_gen",
        schema = "monitoring",
        sequenceName = "projects_seq",
        allocationSize = 1
    )
    @ColumnDefault("nextval('monitoring.projects_seq'")
    @Column(name = "id", nullable = false)
    open var id: Int? = null,

    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    open var name: String,

    @Column(name = "description", length = Integer.MAX_VALUE)
    open var description: String?,

    @NotNull @Column(name = "created_at", nullable = false)
    open var createdAt: Instant = Instant.now(),

    @OneToMany(mappedBy = "project")
    open var alerts: MutableSet<Alert> = mutableSetOf(),

    @OneToMany(mappedBy = "project")
    open var sensors: MutableSet<Sensor> = mutableSetOf(),

    @OneToMany(mappedBy = "project")
    open var userProjectRights: MutableSet<UserProjectRight> = mutableSetOf()
)