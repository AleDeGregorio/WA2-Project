package it.polito.g26.server.security.login

import it.polito.g26.server.security.utils.IntrospectResponse
import it.polito.g26.server.security.utils.Response
import it.polito.g26.server.security.utils.TokenRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate


@Service
class LoginServiceImpl(
    @Value("\${spring.security.oauth2.client.registration.keycloak.client-id}")
    private val clientId: String,
    @Value("\${spring.security.oauth2.client.registration.keycloak.authorization-grant-type}")
    private val grantType: String
) : LoginService {

    val restTemplate = RestTemplate()
    //val tokenUrl = "http://localhost:8080/realms/SpringBoot-Keycloak/protocol/openid-connect/token"
    //val logoutUrl = "http://localhost:8080/realms/SpringBoot-Keycloak/protocol/openid-connect/logout"
    //val introspectUrl = "http://localhost:8080/realms/SpringBoot-Keycloak/protocol/openid-connect/token/introspect"
    val tokenUrl = "http://localhost:8081/realms/ticketingRealm/protocol/openid-connect/token"
    val logoutUrl = "http://localhost:8081/realms/ticketingRealm/protocol/openid-connect/logout"
    val introspectUrl = "http://localhost:8081/realms/ticketingRealm/protocol/openid-connect/token/introspect"

    override fun login(loginRequest: LoginRequest): ResponseEntity<LoginResponse>{
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        val map: MultiValueMap<String,String> = LinkedMultiValueMap<String,String>()
        map["client_id"] = clientId
        map["grant_type"] = grantType
        map["username"] = loginRequest.username
        map["password"] = loginRequest.password

        val httpEntity = HttpEntity<MultiValueMap<String,String>>(map,headers)

        val response: ResponseEntity<LoginResponse> = restTemplate.postForEntity(tokenUrl, httpEntity, LoginResponse::class.java)
        return ResponseEntity<LoginResponse>(response.body, HttpStatus.OK)
    }

    override fun logout(logoutRequest: TokenRequest): ResponseEntity<Response>{
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val map: MutableMap<String, String> = mutableMapOf<String, String>()
        map["client_id"] = clientId
        map["refresh_token"] = logoutRequest.refresh_token

        val httpEntity= HttpEntity<MutableMap<String,String>>(map,headers)
        val response: ResponseEntity<Response> = restTemplate.postForEntity(logoutUrl,httpEntity, Response::class.java)
        if (response.statusCode.is2xxSuccessful){
            response.body?.message = "Logged out successfully"
        }
        return ResponseEntity<Response>(response.body, response.statusCode)
    }

    override fun introspect(tokenRequest: TokenRequest): ResponseEntity<IntrospectResponse> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val map: MutableMap<String, String> = mutableMapOf<String, String>()
        map["client_id"] = clientId
        map["refresh_token"] = tokenRequest.refresh_token

        val httpEntity= HttpEntity<MutableMap<String,String>>(map,headers)
        val response: ResponseEntity<IntrospectResponse> = restTemplate.postForEntity(introspectUrl,httpEntity,
            IntrospectResponse::class.java)

        return ResponseEntity<IntrospectResponse>(response.body, response.statusCode)

    }
}