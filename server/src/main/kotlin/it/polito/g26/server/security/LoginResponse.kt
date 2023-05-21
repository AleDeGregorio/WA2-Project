package it.polito.g26.server.security

class LoginResponse {
    var access_token: String? = ""
    var refresh_token: String? = ""
    var expires_in: Long? = null
    var refresh_expires_in: Long? = null
    var token_type: String? = ""
    var session_state: String? = ""
    var scope: String? = ""
}