package it.polito.g26.server.security.signup

import it.polito.g26.server.profiles.customer.Customer
import it.polito.g26.server.security.login.LoginResponse
import it.polito.g26.server.security.utils.Response
import org.keycloak.admin.client.CreatedResponseUtil
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.representations.idm.UserRepresentation
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Service
class SignupServiceImpl {
    val restTemplate = RestTemplate()

    private val userUrl = "http://localhost:8080/admin/realms/SpringBoot-Keycloak/users"
    fun customerSignup(customer: Customer){
        val keycloak: Keycloak = KeycloakBuilder.builder() //
            .serverUrl("http://localhost:8080") //
            .realm("SpringBoot-Keycloak") //
            .clientId("admin-cli") //
            .username("admin") //
            .password("password") //
            .build();

        println(customer)
        val realmResource = keycloak.realm("SpringBoot-Keycloak")
        //val client = realmResource.clients().findByClientId("springboot-keycloak-client")[0].id

        val userResource = realmResource.users()

        val customerRole = realmResource.roles().get("app_customer").toRepresentation()
        //val customerClientRole = realmResource.clients().get(client).roles().get("customer").toRepresentation()
        val passwordCred = CredentialRepresentation().apply {
            isTemporary = false
            type = CredentialRepresentation.PASSWORD
            value = customer.password
        }
        val customerRep = UserRepresentation().apply {
            isEnabled = true
            id = customer.id.toString()
            username = customer.name+customer.surname
            firstName = customer.name
            lastName = customer.surname
            email = customer.email
            credentials = listOf(passwordCred)
        }

        val response = userResource.create(customerRep)
        /*
        customer.id = userid
        customerRepository.save(customer)
         */
        val userid = CreatedResponseUtil.getCreatedId(response)
        keycloak.realm("SpringBoot-Keycloak")
            .users()
            .get(userid)
            .roles().realmLevel().add(listOf(customerRole))

    }
}

