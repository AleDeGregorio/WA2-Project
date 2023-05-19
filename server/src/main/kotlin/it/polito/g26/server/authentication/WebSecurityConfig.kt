package it.polito.g26.server.authentication

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter




@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    private val jwtAuthConverter: JwtAuthConverter,
    private val authProvider: AuthProvider,
    private val loginServiceImpl: LoginServiceImpl) {

    companion object {
        const val CUSTOMER = "customer"
        const val EXPERT = "expert"
        const val MANAGER = "manager"
    }

    @Bean
    @Throws(java.lang.Exception::class)
    fun authManager(http: HttpSecurity): AuthenticationManager? {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )

        authenticationManagerBuilder.authenticationProvider(authProvider)
        authenticationManagerBuilder.userDetailsService(loginServiceImpl).passwordEncoder(passwordEncoder())

        return authenticationManagerBuilder.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()

        http.authorizeHttpRequests()
            //just testing
            //.requestMatchers(HttpMethod.GET, "/anonymous", "/anonymous/**").permitAll()
            //.requestMatchers(HttpMethod.GET, "/customer", "/customer/**").hasRole(CUSTOMER)
            //.requestMatchers(HttpMethod.GET, "/expert").hasAnyRole(CUSTOMER, EXPERT)
            .requestMatchers(HttpMethod.GET, "/API/manager/{email}").hasRole(MANAGER)
            .requestMatchers(HttpMethod.GET, "/API/products/").authenticated()
            .requestMatchers(HttpMethod.POST, "/API/login").permitAll()
            .requestMatchers(HttpMethod.GET, "/login").permitAll()
            //.anyRequest().authenticated()

        http.oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(jwtAuthConverter)

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        return http.build()
    }
}