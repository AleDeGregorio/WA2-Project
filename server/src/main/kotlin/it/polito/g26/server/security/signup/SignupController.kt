package it.polito.g26.server.security.signup

import it.polito.g26.server.profiles.customer.CustomerDTO
import it.polito.g26.server.profiles.customer.toEntity
import it.polito.g26.server.security.utils.Response
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
) {

    @PostMapping("/signup/customer")
    fun customerSignup(@RequestBody customerDTO: CustomerDTO?, @RequestParam token: String): ResponseEntity<Response> {
        if (customerDTO != null) {
            return signupServiceImpl.CustomerSignup(customerDTO.toEntity(), token)
        } else {
            throw Exception("not body")
        }
    }

    @PostMapping("/signuptoken")
    fun signupToken() {

    }
}