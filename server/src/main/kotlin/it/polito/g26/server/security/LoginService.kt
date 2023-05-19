package it.polito.g26.server.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate


@Service
class LoginServiceImpl(
    @Value("\${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private val issueUrl: String,
    @Value("\${spring.security.oauth2.client.registration.keycloak.client-id}")
    private val clientId: String,
    @Value("\${spring.security.oauth2.resourceserver.jwt.public-key-location}")
    private val clientSecret: String,
    @Value("\${spring.security.oauth2.client.registration.keycloak.authorization-grant-type}")
    private val grantType: String
) {

    val restTemplate = RestTemplate()
    val kcUrl = "http://localhost:8080/realms/SpringBoot-Keycloak/protocol/openid-connect/token"

    fun login(loginRequest: LoginRequest): ResponseEntity<LoginResponse>{
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val map: MultiValueMap<String,String> = LinkedMultiValueMap<String,String>()
        map["client_id"] = clientId
        //map["client_secret"] = listOf(clientSecret)
        map["grant_type"] = grantType
        map["username"] = loginRequest.username
        map["password"] = loginRequest.password

        println("GENERATING MAP URLENCODED")
        println(map)
        //val x : MultiValueMap<String,String> = LinkedMultiValueMap<String,String>()
        //val multimap = CollectionUtils.toMultiValueMap(map)
        val httpEntity = HttpEntity<MultiValueMap<String,String>>(map,headers)
        //val httpEntity= HttpEntity<MultiValueMap<String,String>>(multimap,headers)
        println("HTTPENTITY")
        println(httpEntity.body)
        val response: ResponseEntity<LoginResponse> = restTemplate.postForEntity(kcUrl,httpEntity,LoginResponse::class.java)
        println(response)
        return ResponseEntity<LoginResponse>(response.body, HttpStatus.OK)
    }

    fun logout(loginRequest: LoginRequest): ResponseEntity<LoginResponse>{
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val map: MutableMap<String, String> = mutableMapOf<String, String>()
        map["client_id"] = clientId
        map["client_secret"] = clientSecret
        map["grant_type"] = grantType
        map["username"] = loginRequest.username
        map["password"] = loginRequest.password

        val httpEntity= HttpEntity<MutableMap<String,String>>(map,headers)
        val response: ResponseEntity<LoginResponse> = restTemplate.postForEntity(kcUrl,httpEntity,LoginResponse::class.java)
        return ResponseEntity<LoginResponse>(response.body, HttpStatus.OK)
    }
}