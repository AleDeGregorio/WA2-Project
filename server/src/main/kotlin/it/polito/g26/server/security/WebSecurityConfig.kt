package it.polito.g26.server.security


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val jwtAuthConverter: JwtAuthConverter,
    private val jwtServer: JwtServer
) {
    companion object {
        const val ADMIN = "admin"
        const val MANAGER = "manager"
        const val EXPERT = "expert"
        const val CUSTOMER = "customer"
        const val USER = "user"
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        println(http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthConverter))
        http
            .csrf().disable()
            .oauth2Login()
            .authorizationEndpoint()

        http.authorizeHttpRequests()
            .requestMatchers(HttpMethod.GET,"/customer").hasAnyRole(CUSTOMER)
            .requestMatchers(HttpMethod.GET,"/expert").hasAnyRole(ADMIN, MANAGER, EXPERT)
            .requestMatchers(HttpMethod.GET,"/manager").hasAnyRole(ADMIN, MANAGER)
            .requestMatchers(HttpMethod.GET,"/products").permitAll()

            .requestMatchers(HttpMethod.GET,"/test/anonymous").permitAll()
            .requestMatchers(HttpMethod.GET,"/test/admin").hasRole(ADMIN)
            .requestMatchers(HttpMethod.GET,"/test/user").hasAnyRole(ADMIN, USER)
            .anyRequest().authenticated()

        http.oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(jwtAuthConverter)

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        return http.build()
    }


}
