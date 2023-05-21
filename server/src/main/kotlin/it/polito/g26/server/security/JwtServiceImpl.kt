package it.polito.g26.server.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
/*
@Service
class JwtServiceImpl (
    @Value("\${spring.security.oauth2.resourceserver.jwt.public-key-location}")
    private val jwtSecret: String
){
    fun extractUsername(token: String): String{
        //val jwt = Jwt(token)
        //val claim= jwt.getClaim<String>("sub")
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            //.setSigningKey(getSigninKey())
            .parseClaimsJws(token).body
    }

    private fun extractExpiration(token: String): Date {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.expiration
    }

    fun role(claims: Claims): List<String> {
        val roles = claims["resource_access"]
        println(roles)
        return listOf()
    }

    fun isTokenNotExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return ((username == userDetails.username) && isTokenNotExpired(token))
    }
}*/