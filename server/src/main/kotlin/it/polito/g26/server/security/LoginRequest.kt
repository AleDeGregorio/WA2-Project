package it.polito.g26.server.security

data class LoginRequest (
    var username: String,
    var password: String
)