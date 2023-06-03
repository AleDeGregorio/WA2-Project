package it.polito.g26.server.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "jwt.auth.converter")
data class JwtAuthConverterProperties (
    val resourceId: String,
    val principalAttribute: String
)