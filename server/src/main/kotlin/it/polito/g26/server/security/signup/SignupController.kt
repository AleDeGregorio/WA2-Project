package it.polito.g26.server.security.signup

import it.polito.g26.server.EmptyPostBodyException
import it.polito.g26.server.profiles.customer.Customer
import it.polito.g26.server.profiles.customer.CustomerDTO
import it.polito.g26.server.profiles.customer.toEntity
import it.polito.g26.server.security.login.LoginRequest
import it.polito.g26.server.security.login.LoginResponse
import it.polito.g26.server.security.login.LoginServiceImpl
import it.polito.g26.server.security.utils.Response
import org.keycloak.representations.AccessToken
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class SignupController (
    private val signupServiceImpl: SignupServiceImpl
){

    @PostMapping("/signup/customer")
    fun customerSignup(@RequestBody customerDTO: CustomerDTO?, @RequestParam token: String): ResponseEntity<Response> {
        if (customerDTO != null) {
            return signupServiceImpl.customerSignup(customerDTO.toEntity(), token)
        } else {
            throw EmptyPostBodyException("Empty body for signup")
        }


    }
    @PostMapping("/")
    fun authenticateAdminApi(): ResponseEntity<AccessToken> {
       //modo per trovare client secret
        val clientSecret = "client-secret"
        val accessToken = signupServiceImpl.authenticateAdminApi(clientSecret)
            ?: throw RuntimeException("Authentication failed")

        return ResponseEntity.ok(accessToken)
    }
}