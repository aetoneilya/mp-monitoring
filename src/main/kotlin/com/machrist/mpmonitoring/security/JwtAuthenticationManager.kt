package com.machrist.mpmonitoring.security

import com.machrist.mpmonitoring.common.logger
import com.machrist.mpmonitoring.domain.UserService
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationManager(
    private val jwtService: JwtService,
    private val userService: UserService,
) : ReactiveAuthenticationManager {
    val log by logger()

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val token = authentication.credentials.toString()
        val username = jwtService.extractUserName(token)

        log.info("get username from token $username")

        return userService.reactiveUserDetailsService().findByUsername(username)
            .filter { userDetails -> jwtService.isTokenValid(token, userDetails) }
            .map { userDetails ->
                UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities,
                )
            }
    }

}
