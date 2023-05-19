package it.polito.g26.server.security

data class LoginResponse (
    var accessToken: String,
    var refreshToken: String,
    var expiresIn: String,
    var requestExpiresIn: String,
    var requestType: String
)