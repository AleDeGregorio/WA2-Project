package it.polito.g26.server.profiles.customer

import it.polito.g26.server.EmailNotFoundException
import it.polito.g26.server.UserAlreadyExistException
import it.polito.g26.server.UserNotFoundException
import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.ticketing.tickets.toDTO
import org.keycloak.admin.client.CreatedResponseUtil
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository
) : CustomerService {
    override fun getCustomer(email: String): CustomerDTO? {
        return customerRepository.findByEmail(email)?.toDTO()
    }
    override fun customerSignup(customer: Customer){
        val keycloak: Keycloak = KeycloakBuilder.builder() //
            //.serverUrl("http://localhost:8080") //
            .serverUrl("http://keycloak:8080") //
            //.realm("SpringBoot-Keycloak") //
            .realm("ticketingRealm") //
            .clientId("admin-cli") //
            .username("idm-client") //
            .password("pwd") //
            .build();

        println(customer)
        //val realmResource = keycloak.realm("SpringBoot-Keycloak")
        val realmResource = keycloak.realm("ticketingRealm")
        //val client = realmResource.clients().findByClientId("springboot-keycloak-client")[0].id



        val userResource = realmResource.users()

        val customerRole = realmResource.roles().get("app_customer").toRepresentation()
        //val customerClientRole = realmResource.clients().get(client).roles().get("customer").toRepresentation()
        val passwordCred = CredentialRepresentation().apply {
            isTemporary = false
            type = CredentialRepresentation.PASSWORD
            value = customer.password
        }
        val attr: MutableMap<String, List<String>> = mutableMapOf();
        attr["city"] = listOf(customer.city)
        attr["address"] = listOf(customer.address)

        val customerRep = UserRepresentation().apply {
            isEnabled = true
            username = customer.username
            firstName = customer.firstName
            lastName = customer.lastName
            email = customer.email
            credentials = listOf(passwordCred)
            attributes = attr
        }

        val response = userResource.create(customerRep)
        val userid = CreatedResponseUtil.getCreatedId(response)
        customer.id = userid
         if (customerRepository.existsByEmail(customerRep.email)) {
            throw UserAlreadyExistException("Customer with id ${customerRep.id} already exist")
        }
        else {
            customerRepository.save(customer)
        }


        //keycloak.realm("SpringBoot-Keycloak")
        keycloak.realm("ticketingRealm")
            .users()
            .get(userid)
            .roles().realmLevel().add(listOf(customerRole))
        //println("response status ${response.status}, response info status ${response.statusInfo}\n reponse body ${response} and \n userid ${userid}")
        //println(customerRep.credentials[0].value)
    }



    override fun updateCustomer(customer: Customer) {
        //Keycloak update
        val keycloak: Keycloak = KeycloakBuilder.builder()
            //.serverUrl("http://localhost:8080")
            .serverUrl("http://keycloak:8080")
            //.realm("SpringBoot-Keycloak")
            .realm("ticketingRealm")
            .clientId("admin-cli")
            .username("idm-client")
            .password("pwd")
            .build()


        //val realmResource = keycloak.realm("SpringBoot-Keycloak")
        val realmResource = keycloak.realm("ticketingRealm")
        val userResource = realmResource.users()

        val user: UserRepresentation = userResource[customer.id].toRepresentation()
            ?: throw UserNotFoundException("Customer with id ${customer.id} not found in keycloak!")

        val userId = user.id

        val attr: MutableMap<String, List<String>> = mutableMapOf()
        attr["city"] = listOf(customer.city)
        attr["address"] = listOf(customer.address)

        /* val passwordCred = CredentialRepresentation().apply {
             isTemporary = false
             type = CredentialRepresentation.PASSWORD
             value = customer.password
         }*/

        // Update the attributes of the user
        user.attributes = attr
        user.lastName =customer.lastName
        user.username = customer.username
        user.firstName = customer.firstName
        //user.credentials= listOf(passwordCred) doesn't update here?

        // Update the user in Keycloak
        userResource.get(userId).update(user)
        if (customerRepository.existsByEmail(customer.email)) {
            val retrievedCustomer = customerRepository.findByEmail(customer.email)

            retrievedCustomer?.username = customer.username
            retrievedCustomer?.firstName = customer.firstName
            retrievedCustomer?.lastName = customer.lastName
            retrievedCustomer?.city = customer.city
            retrievedCustomer?.address = customer.address

            customerRepository.save(retrievedCustomer!!)
        }
        else {
            throw UserNotFoundException("Customer with id ${customer.id} not found!")
        }
    }

    override fun getTickets(id: String): Set<TicketDTO>? {
        if (customerRepository.existsById(id)) {
            val tickets = customerRepository.getTickets(id) ?: return null
            return tickets.map { it.toDTO() }.toSet()
        }
        else {
            throw EmailNotFoundException("Customer not found!")
        }
    }
}