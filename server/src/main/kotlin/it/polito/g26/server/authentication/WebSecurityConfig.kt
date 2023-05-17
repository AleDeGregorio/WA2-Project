package it.polito.g26.server.authentication

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig(private val jwtAuthConverter: JwtAuthConverter) {

    companion object {
        const val CUSTOMER = "customer"
        const val EXPERT = "expert"
        const val MANAGER = "manager"
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests()
            //just testing
            //.requestMatchers(HttpMethod.GET, "/anonymous", "/anonymous/**").permitAll()
            //.requestMatchers(HttpMethod.GET, "/customer", "/customer/**").hasRole(CUSTOMER)
            //.requestMatchers(HttpMethod.GET, "/expert").hasAnyRole(CUSTOMER, EXPERT)
            .requestMatchers(HttpMethod.GET, "/API/manager/{email}").hasRole(MANAGER)
            .anyRequest().authenticated()
        http.oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(jwtAuthConverter)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        return http.build()
    }
}