package com.machrist.mpmonitoring.security

import com.machrist.mpmonitoring.common.logger
import com.machrist.mpmonitoring.domain.UserService
import com.machrist.mpmonitoring.model.User
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userService: UserService,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val reactiveAuthenticationManager: ReactiveAuthenticationManager,
) {
    val log by logger()

    fun signUp(
        username: String,
        password: String,
    ): String {
        val user =
            User(
                login = username,
                passwordHash = passwordEncoder.encode(password),
            )

        userService.create(user)

        return jwtService.generateToken(user)
    }

    suspend fun signIn(
        username: String,
        password: String,
    ): String {
        reactiveAuthenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
            .awaitSingleOrNull()

        val user = userService.userDetailsService().loadUserByUsername(username)

        return jwtService.generateToken(user)
    }

    fun hasAccessToProject(
        projectId: String,
        user: UserDetails,
    ): Boolean {
        log.info("securoty project : $projectId user: ${user.username}")
        return true
    }
}
