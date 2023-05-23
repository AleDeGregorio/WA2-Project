package it.polito.g26.server.security.login

import it.polito.g26.server.security.utils.IntrospectResponse
import it.polito.g26.server.security.utils.Response
import it.polito.g26.server.security.utils.TokenRequest
import org.springframework.http.ResponseEntity

interface LoginService {
    fun login(loginRequest: LoginRequest): ResponseEntity<LoginResponse>
    fun logout(tokenRequest: TokenRequest): ResponseEntity<Response>
    fun introspect(tokenRequest: TokenRequest): ResponseEntity<IntrospectResponse>
}