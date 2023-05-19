package it.polito.g26.server.authentication

import it.polito.g26.server.profiles.customer.CustomerRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class LoginServiceImpl(
    private val authProvider: AuthProvider, private val customerRepository: CustomerRepository
) : LoginService, UserDetailsService {

    override fun login(username: String, password: String): Authentication {
        val authentication = UsernamePasswordAuthenticationToken(username, password)
        return authProvider.authenticate(authentication)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val customer = customerRepository.findByEmail(username!!)

        return User.withUsername(customer!!.email).authorities("customer").build()
    }
}