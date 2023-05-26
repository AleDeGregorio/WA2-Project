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
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
2
        http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST,"/product/**").hasAnyRole(ADMIN,MANAGER)
            .requestMatchers(HttpMethod.PUT,"/product/**").hasAnyRole(ADMIN,MANAGER)
            .requestMatchers(HttpMethod.PATCH,"/product/**").hasAnyRole(ADMIN,MANAGER)
            .requestMatchers("/customer/**").hasAnyRole(ADMIN,MANAGER,EXPERT,CUSTOMER)
            .requestMatchers(HttpMethod.GET,"/expert/**").hasAnyRole(ADMIN, MANAGER,EXPERT)
            .requestMatchers(HttpMethod.POST,"/expert/**").hasRole(MANAGER)
            .requestMatchers(HttpMethod.PUT,"/expert/**").hasAnyRole(ADMIN, MANAGER)
            .requestMatchers("/manager/**").hasAnyRole(ADMIN,MANAGER)
            .requestMatchers("/ticket/**").hasAnyRole(ADMIN,MANAGER)
            .requestMatchers(HttpMethod.POST,"/ticket").hasRole(CUSTOMER)
            .requestMatchers(HttpMethod.PUT,"/ticket/{id}/expert").hasAnyRole(ADMIN, MANAGER)
            .requestMatchers(HttpMethod.PUT,"/ticket/{id}/priority").hasAnyRole(ADMIN, MANAGER)
            .requestMatchers("/ticket/status/**").hasAnyRole(ADMIN,MANAGER, EXPERT)
            .requestMatchers("/ticket/{id}/status/**").hasAnyRole(ADMIN,MANAGER, EXPERT)
            .requestMatchers(HttpMethod.GET,"/ticket/").permitAll()
            .requestMatchers(HttpMethod.POST,"/auth/**").permitAll()
            .requestMatchers(HttpMethod.GET,"/product/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/customer").permitAll()
            .anyRequest().authenticated()

        //.requestMatchers("/API/ticket/product/**").hasAnyRole(ADMIN, MANAGER, EXPERT)
        //.requestMatchers("/API/ticket/customer/**").hasAnyRole(ADMIN, MANAGER, EXPERT)
        //.requestMatchers("/API/ticket/product/**").hasAnyRole(ADMIN, MANAGER, EXPERT)
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
