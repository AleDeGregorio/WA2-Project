package it.polito.g26.server.authentication

import org.springframework.security.core.Authentication

interface LoginService {
    fun login(username: String, password: String): Authentication
}