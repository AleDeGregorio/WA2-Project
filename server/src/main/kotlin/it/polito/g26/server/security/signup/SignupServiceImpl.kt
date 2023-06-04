package it.polito.g26.server.security.signup

import it.polito.g26.server.profiles.customer.Customer
import it.polito.g26.server.security.login.LoginResponse
import it.polito.g26.server.security.utils.Response
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.representations.idm.UserRepresentation
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Service
class SignupServiceImpl {
    val restTemplate = RestTemplate()

    private val userUrl = "http://localhost:8080/admin/realms/SpringBoot-Keycloak/users"
    fun customerSignup(customer: Customer): ResponseEntity<Response> {
        val keycloak: Keycloak = KeycloakBuilder.builder() //
            .serverUrl("http://localhost:8080") //
            .realm("SpringBoot-Keycloak") //
            .clientId("admin-cli") //
            .username("admin") //
            .password("password") //
            .build();

        val realmResource = keycloak.realm("SpringBoot-Keycloak")
        val client = realmResource.clients().findByClientId("springboot-keycloak-client")[0]

        val userUrl = "http://localhost:8080/admin/realms/SpringBoot-Keycloak/users"
        val tokenUrl = "http://localhost:8080/realms/SpringBoot-Keycloak/protocol/openid-connect/token"
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        val map: MultiValueMap<String, String> = LinkedMultiValueMap<String, String>()
        map["client_id"] = "admin-cli"
        map["grant_type"] = "password"
        map["username"] = "admin"
        map["password"] = "password"

        val httpEntity = HttpEntity<MultiValueMap<String, String>>(map, headers)

        val response: ResponseEntity<LoginResponse> = restTemplate.postForEntity(tokenUrl, httpEntity, LoginResponse::class.java)
        var token: String = ""
        if (response.statusCode.is2xxSuccessful) {
            token = response.body?.access_token!!
            println(token)
        } else {
            throw Exception("Invalid Request")
        }

        headers.contentType = MediaType.APPLICATION_JSON
        headers.setBearerAuth(token)
        val customerRole = realmResource.roles().get("app_customer").toRepresentation()
        val customerClientRole = realmResource.clients().get(client.id).roles().get("customer").toRepresentation()
        val passwordCred = CredentialRepresentation().apply {
            isTemporary = false
            type = CredentialRepresentation.PASSWORD
            value = customer.password
        }
        val customerRep = UserRepresentation().apply {
            isEnabled = true
            username = "customer-${customer.name}"
            firstName = customer.name
            lastName = customer.surname
            email = customer.email
            credentials = listOf(passwordCred)
            realmRoles = listOf(customerRole.toString())
        }

        println(headers)
        println(customerRep.toString())




        val httpEntity2 = HttpEntity<UserRepresentation>(customerRep, headers)
        println(httpEntity2)
        val response2: ResponseEntity<Response> = restTemplate.exchange(userUrl, HttpMethod.POST, httpEntity2, Response::class.java)
        println(response2)
        return ResponseEntity<Response>(response2.body, response2.statusCode)
    }
}

