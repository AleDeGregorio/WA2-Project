package it.polito.g26.server.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.stereotype.Component

@Component
class JwtServer (
    @Value("\${spring.security.oauth2.resourceserver.jwt.public-key-location}")
    private val secretKey: String
) {
/*
    @Bean
    fun jwtDecoder(): JwtDecoder{
        val key = SecretKeySpec(secretKey.toByteArray(), "RS256")
        return NimbusJwtDecoder
            .withSecretKey(key)
            .macAlgorithm(MacAlgorithm.HS256)
            .build()
    }

 */

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter{
        println("ROLE READ")
        val converter = JwtAuthenticationConverter()
        converter.setJwtGrantedAuthoritiesConverter {
                jwt: Jwt -> jwt
            .getClaim<String>("roles")
            .split(",")
            .map { GrantedAuthority {it} }
        }
        return converter
    }

    /*
    @Bean
    fun jwtEncoder(): JwtEncoder{
        val key = SecretKeySpec(secretKey.toByteArray(), "HMACSHA256")
        val immutableSecret = ImmutableSecret<SecurityContext>(key)
        return NimbusJwtEncoder(immutableSecret)
    }

    fun createJWT(username: String, roles: List<String>): String? {
        val header = JwsHeader.with{"HS256"}.build()
        val claimsSet = JwtClaimsSet.builder()
            .issuer("localhost")
            .subject(username)
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusMillis(jwtExpiration))
            .claim("roles", roles.joinToString(",") )
            .build()

        return jwtEncoder()
            .encode(JwtEncoderParameters.from(header, claimsSet))
            .tokenValue
    }*/
}