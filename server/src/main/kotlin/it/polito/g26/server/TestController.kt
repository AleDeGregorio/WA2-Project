package it.polito.g26.server

import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
@RequestMapping("/test")
class TestController {

    @GetMapping("/anonymous")
    fun getAnonymous(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello Anonymous");
    }

    @GetMapping("/admin")
    fun getAdmin(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello Admin");
    }

    @GetMapping("/user")
    fun getUser(principal: Principal): ResponseEntity<String> {
        val token= principal as JwtAuthenticationToken
        val userName = token.tokenAttributes.get("name") as String
        val userEmail = token.tokenAttributes.get("email") as String

        return ResponseEntity.ok("Hello User \nUser Name : $userName\nUser Email : $userEmail")
    }
}




