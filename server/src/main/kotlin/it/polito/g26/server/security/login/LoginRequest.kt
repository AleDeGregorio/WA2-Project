package it.polito.g26.server.security.login

data class LoginRequest (
    var username: String,
    var password: String
)