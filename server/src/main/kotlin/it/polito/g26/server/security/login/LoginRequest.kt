package it.polito.g26.server.security.login

data class LoginRequest (
    var username: String,
    var password: String
) {
    override fun toString() : String {
        return "Username: ${username}, Password: ${password}"
    }
}