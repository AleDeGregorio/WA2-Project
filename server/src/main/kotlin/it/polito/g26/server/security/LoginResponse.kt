package it.polito.g26.server.security

class LoginResponse {
    var access_token: String? = ""
    var refresh_token: String? = ""
    var expires_in: String? = ""
    var refresh_expires_in: String? = ""
    var token_type: String? = ""
    var session_state: String? = ""
    var scope: String? = ""
}