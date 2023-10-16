package it.polito.g26.server.security.signup

import it.polito.g26.server.profiles.customer.Customer
import it.polito.g26.server.security.login.LoginRequest
import it.polito.g26.server.security.login.LoginResponse
import it.polito.g26.server.security.utils.IntrospectResponse
import it.polito.g26.server.security.utils.Response
import it.polito.g26.server.security.utils.TokenRequest
import org.keycloak.representations.AccessToken
import org.springframework.http.ResponseEntity

interface SignupService{

        fun customerSignup(customer: Customer, token: String) : ResponseEntity<Response>
        fun authenticateAdminApi(clientSecret: String): AccessToken?

}