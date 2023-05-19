package it.polito.g26.server.authentication

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(private val loginService: LoginServiceImpl)  {

    @PostMapping("/API/login")
    fun login(@RequestBody loginRequest: LoginRequest) : ResponseEntity<Any> {
        val username = loginRequest.username
        val password = loginRequest.password

        try {
            val authentication = loginService.login(username, password)

            return ResponseEntity.ok("Login successful!, this is your token:\n$authentication")
        }
        catch (ex: AuthenticationException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed")
        }
    }
}