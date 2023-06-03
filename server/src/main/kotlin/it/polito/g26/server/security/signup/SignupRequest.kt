package it.polito.g26.server.security.signup


data class SignupRequest (
    var name: String,
    var surname: String,
    var email: String,
    var password: String,
    var city: String,
    var address: String
)