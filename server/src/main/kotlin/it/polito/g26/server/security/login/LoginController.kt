package it.polito.g26.server.security.login

import io.micrometer.observation.annotation.Observed
import it.polito.g26.server.security.*
import it.polito.g26.server.security.utils.IntrospectResponse
import it.polito.g26.server.security.utils.Response
import it.polito.g26.server.security.utils.TokenRequest
import org.slf4j.LoggerFactory
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
@Observed
@Slf4j
class LoginController (
    private val loginServiceImpl: LoginServiceImpl
){

    private val log = LoggerFactory.getLogger(LoginController::class.java)

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse>{
        log.info("User login request: {}", loginRequest.toString())
        return loginServiceImpl.login(loginRequest)
    }

    @PostMapping("/logout")
    fun logout(@RequestBody tokenRequest: TokenRequest): ResponseEntity<Response>{
        log.info("User logging out")
        return loginServiceImpl.logout(tokenRequest)
    }

    @PostMapping("/introspect")
    fun introspect(@RequestBody tokenRequest: TokenRequest): ResponseEntity<IntrospectResponse>{
        return loginServiceImpl.introspect(tokenRequest)
    }
}