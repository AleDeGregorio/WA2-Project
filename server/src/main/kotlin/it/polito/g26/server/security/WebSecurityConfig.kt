package it.polito.g26.server.security


import it.polito.g26.server.security.jwt.JwtAuthConverter
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import javax.ws.rs.POST

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val jwtAuthConverter: JwtAuthConverter
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

        http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST,"/API/auth/**").permitAll()
            .requestMatchers(HttpMethod.GET,"/API/products**").permitAll()
            .requestMatchers(HttpMethod.POST,"/API/products**").hasAnyRole(ADMIN,MANAGER)
            .requestMatchers(HttpMethod.PUT,"/API/products**").hasAnyRole(ADMIN,MANAGER)
            .requestMatchers(HttpMethod.PATCH,"/API/products**").hasAnyRole(ADMIN,MANAGER)
            .requestMatchers("/API/customer/**").hasAnyRole(ADMIN,MANAGER,EXPERT,CUSTOMER)
            .requestMatchers(HttpMethod.POST, "/API/customer").permitAll()
            .requestMatchers(HttpMethod.GET,"/API/expert/**").hasAnyRole(ADMIN, MANAGER,EXPERT)
            .requestMatchers(HttpMethod.POST,"/API/expert/**").hasAnyRole(ADMIN, MANAGER)
            .requestMatchers(HttpMethod.PUT,"/API/expert/**").hasAnyRole(ADMIN, MANAGER)
            .requestMatchers("/API/manager/**").hasAnyRole(ADMIN,MANAGER)
            .requestMatchers("/API/ticket/**").hasAnyRole(ADMIN,MANAGER)
            //.requestMatchers("/API/ticket/product/**").hasAnyRole(ADMIN, MANAGER, EXPERT)
            //.requestMatchers("/API/ticket/customer/**").hasAnyRole(ADMIN, MANAGER, EXPERT)
            //.requestMatchers("/API/ticket/product/**").hasAnyRole(ADMIN, MANAGER, EXPERT)
            .requestMatchers(HttpMethod.GET,"/API/ticket/").permitAll()
            .requestMatchers(HttpMethod.POST,"/API/ticket").hasRole(CUSTOMER)
            .requestMatchers(HttpMethod.PUT,"/API/ticket/{id}/expert").hasAnyRole(ADMIN, MANAGER)
            .requestMatchers(HttpMethod.PUT,"/API/ticket/{id}/priority").hasAnyRole(ADMIN, MANAGER)
            .requestMatchers("/API/ticket/status/**").hasAnyRole(ADMIN,MANAGER, EXPERT)
            .requestMatchers("/API/ticket/{id}/status/**").hasAnyRole(ADMIN,MANAGER, EXPERT)
            .anyRequest().authenticated()

        http.oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(jwtAuthConverter)

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        return http.build()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        val kcAuthProvider = KeycloakAuthenticationProvider()
        kcAuthProvider.setGrantedAuthoritiesMapper(SimpleAuthorityMapper())
        auth.authenticationProvider(kcAuthProvider)
    }

    @Bean
    @Override
    protected fun sessionAuthenticationStrategy():SessionAuthenticationStrategy {
        return RegisterSessionAuthenticationStrategy(SessionRegistryImpl())
    }


}
