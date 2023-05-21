package it.polito.g26.server.security


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
            .requestMatchers(HttpMethod.POST,"/auth/**").permitAll()
            .requestMatchers(HttpMethod.GET,"/api/product").permitAll()
            .requestMatchers(HttpMethod.POST,"/api/product**").hasAnyRole(ADMIN,MANAGER)
            .requestMatchers(HttpMethod.PUT,"/api/product**").hasAnyRole(ADMIN,MANAGER)
            .requestMatchers(HttpMethod.PATCH,"/api/product**").hasAnyRole(ADMIN,MANAGER)
            .requestMatchers("/api/customer**").hasAnyRole(ADMIN,MANAGER,EXPERT,CUSTOMER)
            .requestMatchers("/api/expert**").hasAnyRole(ADMIN,MANAGER,EXPERT)
            .requestMatchers("/api/manager**").hasAnyRole(ADMIN,MANAGER)
            .requestMatchers("/api/ticket**").hasAnyRole(ADMIN,MANAGER, EXPERT, CUSTOMER)
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
