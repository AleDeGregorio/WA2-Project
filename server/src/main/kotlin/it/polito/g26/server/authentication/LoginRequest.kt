package it.polito.g26.server.authentication

data class LoginRequest(
    val grant_type: String = "password",
    val client_id: String = "springboot-keycloak-client",
    val username: String,
    val password: String
)