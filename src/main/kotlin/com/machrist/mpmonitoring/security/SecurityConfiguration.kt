package com.machrist.mpmonitoring.security

import com.machrist.mpmonitoring.common.logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.web.cors.CorsConfiguration
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfiguration(
    private val jwtAuthenticationManager: JwtAuthenticationManager,
) {
    private val log by logger()
    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf { it.disable() }
            .cors { cors ->
                cors.configurationSource {
                    val corsConfiguration = CorsConfiguration()
                    corsConfiguration.setAllowedOriginPatterns(listOf("*"))
                    corsConfiguration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    corsConfiguration.allowedHeaders = listOf("*")
                    corsConfiguration.allowCredentials = true
                    corsConfiguration
                }
            }
            .authorizeExchange { exchange ->
                exchange
                    .pathMatchers("/auth/**").permitAll()
                    .pathMatchers("/swagger-ui/", "/swagger-resources/*", "/v3/api-docs/").permitAll()
                    .anyExchange().authenticated()
            }
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .authenticationManager(jwtAuthenticationManager)
            .addFilterAt(
                AuthenticationWebFilter(jwtAuthenticationManager).apply {
                    setServerAuthenticationConverter(jwtAuthenticationConverter())
                },
                SecurityWebFiltersOrder.AUTHENTICATION,
            )
            .build()
    }

    private fun jwtAuthenticationConverter(): ServerAuthenticationConverter {
        return ServerAuthenticationConverter { exchange ->
            val token = exchange.request.headers.getFirst("Authorization")
            if (token != null && token.startsWith("Bearer ")) {
                val authToken = token.substring(7)
                Mono.just(UsernamePasswordAuthenticationToken(authToken, authToken))
            } else {
                Mono.empty()
            }
        }
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
