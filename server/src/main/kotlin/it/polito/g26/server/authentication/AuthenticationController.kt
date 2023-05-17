package it.polito.g26.server.authentication

import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

//just testing, not to be used
//to be deleted
@RestController
class AuthenticationController {

    @GetMapping("/anonymous")
    fun getAnonymous(): ResponseEntity<String?>? {
        return ResponseEntity.ok("Hello Anonymous")
    }

    @GetMapping("/customer")
    fun getCustomer(principal: Principal): ResponseEntity<String?>? {
        val token = principal as JwtAuthenticationToken
        val userName = token.tokenAttributes["name"] as String?
        val userEmail = token.tokenAttributes["email"] as String?
        return ResponseEntity.ok("Hello Admin \nUser Name : $userName\nUser Email : $userEmail")
    }

    @GetMapping("/expert")
    fun getExpert(principal: Principal): ResponseEntity<String?>? {
        val token = principal as JwtAuthenticationToken
        val userName = token.tokenAttributes["name"] as String?
        val userEmail = token.tokenAttributes["email"] as String?
        return ResponseEntity.ok("Hello User \nUser Name : $userName\nUser Email : $userEmail")
    }
}