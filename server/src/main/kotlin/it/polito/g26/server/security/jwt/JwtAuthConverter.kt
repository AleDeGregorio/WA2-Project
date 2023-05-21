package it.polito.g26.server.security.jwt

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimNames
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.stereotype.Component
import java.util.stream.Collectors
import java.util.stream.Stream


@Component
class JwtAuthConverter (
    private val properties: JwtAuthConverterProperties
) : Converter<Jwt, AbstractAuthenticationToken> {
    private val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
    override fun convert(jwt: Jwt): AbstractAuthenticationToken? {
        val authorities = Stream.concat(
            jwtGrantedAuthoritiesConverter.convert(jwt)?.stream()!!,
            extractResourceRoles(jwt).stream()).collect(Collectors.toSet())
        return JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt))

    }

    private fun getPrincipalClaimName(jwt: Jwt): String? {
        var claimName = JwtClaimNames.SUB
        if (properties.principalAttribute != "" && properties.principalAttribute != null) {
            claimName = properties.principalAttribute
        }
        return jwt.getClaim(claimName)
    }

    private fun extractResourceRoles(jwt: Jwt): Collection<GrantedAuthority> {
        val resourceAccess = jwt.getClaimAsMap("resource_access")
        val resource: MutableMap<String, Any> = resourceAccess[properties.resourceId] as MutableMap<String, Any>
        val resourceRoles: Collection<String> = resource["roles"] as Collection<String>

        if ((resourceAccess == null)
            || (resource.isEmpty())
            || (resourceRoles.isEmpty())
        ) {
            return emptyList()
        }
        return resourceRoles.stream()
            .map{SimpleGrantedAuthority("ROLE_$it")}
            .collect(Collectors.toSet())
    }
}