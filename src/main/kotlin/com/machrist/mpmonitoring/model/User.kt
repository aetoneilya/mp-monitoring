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
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant

@Entity
@Table(schema = "monitoring", name = "users")
open class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_gen")
    @SequenceGenerator(name = "users_id_gen", schema = "monitoring", sequenceName = "users_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    open var id: Int? = null,
    @NotNull
    @Column(name = "login", nullable = false)
    open var login: String,
    @NotNull
    @Column(name = "password", nullable = false)
    open var passwordHash: String,
    @NotNull
    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant = Instant.now(),
    @OneToMany(mappedBy = "user")
    open var userProjectRights: MutableSet<UserProjectRight> = mutableSetOf(),
) : UserDetails {
    override fun getAuthorities() = listOf(SimpleGrantedAuthority("ROLE_USER"))

    override fun getPassword() = passwordHash

    override fun getUsername() = login

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
