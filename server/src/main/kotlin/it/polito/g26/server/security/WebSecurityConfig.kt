package it.polito.g26.server.security


import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy

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
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST,"/auth/**").permitAll()

        /*http.authorizeHttpRequests()
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


         */
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

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
