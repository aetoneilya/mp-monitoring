package com.machrist.mpmonitoring.model

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@Table(schema = "monitoring", name = "user_project_rights")
open class UserProjectRight(
    @EmbeddedId
    @SequenceGenerator(name = "user_project_rights_id_gen",schema = "monitoring", sequenceName = "users_seq", allocationSize = 1)
    open var id: UserProjectRightId?,

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: User?,

    @MapsId("projectId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    open var project: Project?,

    @Column(name = "access_right", columnDefinition = "access_rights not null")
    open var accessRight: AccessRights?
)