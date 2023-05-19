package it.polito.g26.server.security

data class LoginResponse (
    var accessToken: String?,
    var refreshToken: String?,
    var expiresIn: String?,
    var refreshExpiresIn: String?,
    var tokenType: String?,
    var notBeforePolicy: String?,
    var sessionState: String?,
    var scope: String?
)