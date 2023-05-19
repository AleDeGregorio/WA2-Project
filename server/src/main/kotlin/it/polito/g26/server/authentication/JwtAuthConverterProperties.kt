package it.polito.g26.server.authentication

import lombok.Data
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated


@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
class JwtAuthConverterProperties {

    val resourceId = "springboot-keycloak-client"
    val principalAttribute = "preferred_username"
}