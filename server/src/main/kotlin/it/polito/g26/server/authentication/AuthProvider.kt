package it.polito.g26.server.authentication

import org.json.JSONObject
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Component
class AuthProvider : AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication {
        val username = authentication!!.name
        val password = authentication.credentials.toString()

        val keycloakUrl = "http://localhost:8081/realms/ticketingRealm/protocol/openid-connect/token"
        val clientId = "springboot-keycloak-client"

        val client = WebClient.create()
        val formData = BodyInserters.fromFormData("grant_type", "password")
            .with("client_id", clientId)
            .with("username", username)
            .with("password", password)

        val response = client.post()
            .uri(keycloakUrl)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(formData)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        if (response != null) {
            val jsonObject = JSONObject(response)
            val accessToken = jsonObject.getString("access_token")

            val bt = BearerTokenAuthenticationToken(accessToken)

            /*
            val jwt = Jwt
                .withTokenValue(bt.principal.toString())
                .header("alg", "RS256")
                .header("typ", "JWT")
                .header("kid", "b4IfX9LNBlBTnbLHl4zF1-Yo12dfKOBlbBpPcXyGCCA")
                .subject(username)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(86400000))
                .build()

            return JwtAuthenticationToken(jwt)

             */
            SecurityContextHolder.getContext().authentication = bt
            return bt
        }
        else {
            throw RuntimeException("Failed to obtain access token from Keycloak")
        }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication!! == UsernamePasswordAuthenticationToken::class.java || authentication == BearerTokenAuthenticationToken::class.java
    }
}