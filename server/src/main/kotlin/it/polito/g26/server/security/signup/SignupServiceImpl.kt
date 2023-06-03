package it.polito.g26.server.security.signup

import it.polito.g26.server.profiles.customer.Customer
import it.polito.g26.server.security.utils.Response
import org.keycloak.representations.AccessToken
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class SignupServiceImpl(
    @Value("\${spring.security.oauth2.client.registration.keycloak.client-id}")
    private val clientId: String,
    @Value("\${spring.security.oauth2.client.registration.keycloak.authorization-grant-type}")
    private val grantType: String
): SignupService {

    val restTemplate = RestTemplate();
    val tokenUrl = "http://localhost:8080/realms/SpringBoot-Keycloak/protocol/openid-connect/token";
    override fun authenticateAdminApi(clientSecret: String): AccessToken? {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val requestBody = "grant_type=client_credentials&client_id=$clientId&client_secret=$clientSecret"
        val requestEntity = HttpEntity(requestBody, headers)
        val responseEntity: ResponseEntity<AccessToken> = restTemplate.exchange(
            tokenUrl,
            HttpMethod.POST,
            requestEntity,
            AccessToken::class.java
        )

        if (responseEntity.statusCode.is2xxSuccessful) {
            return responseEntity.body
        } else {
            throw RuntimeException("Failed to authenticate with Keycloak Admin API. Status code: ${responseEntity.statusCodeValue}")
        }
    }

    override fun customerSignup(customer: Customer, token: String) : ResponseEntity<Response> {

        val userUrl = "http://localhost:8080/auth/admin/realms/Springboot-Keycloak/users"
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.setBearerAuth(token)
        val userObj = JSONObject()
        val credentialObj = JSONObject()
        credentialObj.put("type", "password")
        credentialObj.put("temporary",false)
        credentialObj.put("password", customer.password)
        userObj.put("email", customer.email)
        userObj.put("emailVerified", false)
        userObj.put("enabled", false)
        userObj.put("firstName",customer.name)
        userObj.put("lastName",customer.surname)
        userObj.put("realmRoles", arrayOf<String>("customer"))
        userObj.put("username",customer.id)
        userObj.put("credentials", arrayOf(credentialObj))

        val httpEntity = HttpEntity<String>(userObj.toString(), headers)
        val response : ResponseEntity<Response> = restTemplate.postForEntity(userUrl, httpEntity, Response::class.java)

        return ResponseEntity<Response>(response.body, response.statusCode)
    }
}