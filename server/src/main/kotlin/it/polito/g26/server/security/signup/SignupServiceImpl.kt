package it.polito.g26.server.security.signup

import it.polito.g26.server.profiles.customer.Customer
import it.polito.g26.server.security.utils.Response
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class SignupServiceImpl {
    val restTemplate = RestTemplate()

    private val userUrl = "http://localhost:8080/admin/realms/SpringBoot-Keycloak/users"
    fun CustomerSignup(customer: Customer, token: String): ResponseEntity<Response>{
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