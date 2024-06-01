package com.machrist.mpmonitoring.controllers

import com.machrist.mpmonitoring.domain.UserService
import com.machrist.mpmonitoring.openapi.UsersApi
import com.machrist.mpmonitoring.openapi.dto.SignInRequest
import com.machrist.mpmonitoring.openapi.dto.SignUp201Response
import com.machrist.mpmonitoring.openapi.dto.SignUpRequest

import com.machrist.mpmonitoring.security.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UsersController(val userService: UserService, val authenticationService: AuthenticationService) : UsersApi {
    override suspend fun signUp(signUpRequest: SignUpRequest): ResponseEntity<SignUp201Response> {
        return ResponseEntity.ok(
            SignUp201Response(
                authenticationService.signUp(
                    signUpRequest.login,
                    signUpRequest
                        .password,
                ),
            ),
        )
    }

    override suspend fun signIn(signInRequest: SignInRequest): ResponseEntity<SignUp201Response> {
        return ResponseEntity.ok(
            SignUp201Response(
                authenticationService.signIn(
                    signInRequest.login,
                    signInRequest.password,
                ),
            ),
        )
    }
}
