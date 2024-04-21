package com.machrist.mpmonitoring.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.Objects

@Embeddable
open class UserProjectRightId(
    @NotNull
    @Column(name = "user_id", nullable = false)
    open var userId: Int?,
    @NotNull
    @Column(name = "project_id", nullable = false)
    open var projectId: Int?,
) : Serializable {
    override fun hashCode(): Int = Objects.hash(userId, projectId)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as UserProjectRightId

        return userId == other.userId &&
            projectId == other.projectId
    }
}
