package it.polito.g26.server.security
/*
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.lang.NonNull
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtService: JwtServiceImpl
): OncePerRequestFilter() {

    override fun doFilterInternal(
        @NonNull
        request: HttpServletRequest,
        @NonNull
        response: HttpServletResponse,
        @NonNull
        filterChain: FilterChain
    ) {
        val authHeader: String = request.getHeader("Authorization")
        val jwt: String
        val username: String

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response)
            return
        }

        jwt = authHeader.substring(7)
        println(jwt)
        //username = jwtService.extractUsername(jwt).toString()

    }
}
 */