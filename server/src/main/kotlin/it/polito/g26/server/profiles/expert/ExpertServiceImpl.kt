package it.polito.g26.server.profiles.expert

import it.polito.g26.server.EmailAlreadyExistException
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
import org.springframework.transaction.annotation.Transactional

@Service
class ExpertServiceImpl(
    private val expertRepository: ExpertRepository
) : ExpertService {
    override fun getExpert(email: String): ExpertDTO? {
        return expertRepository.getByEmail(email)?.toDTO()
    }

    override fun getExpertsByField(field: String): List<ExpertDTO> {
        return expertRepository.getByField(field).map { it.toDTO() }
    }

    @Transactional
    override fun insertExpert(expert: Expert) {
        val keycloak: Keycloak = KeycloakBuilder.builder() //
            .serverUrl("http://localhost:8080") //
            //.serverUrl("http://keycloak:8080") //
            .realm("SpringBoot-Keycloak") //
            //.realm("ticketingRealm") //
            .clientId("admin-cli") //
            .username("idm-client") //
            .password("pwd") //
            .build();

        println(expert)
        val realmResource = keycloak.realm("SpringBoot-Keycloak")
        //val realmResource = keycloak.realm("ticketingRealm")
        //val client = realmResource.clients().findByClientId("springboot-keycloak-client")[0].id


        val userResource = realmResource.users()
        if (userResource.search(expert.username).isNotEmpty() ) {
            throw UserAlreadyExistException("Expert ${expert.username} already exists")
        } else if(userResource.search(expert.email, 0, 1).isNotEmpty()) {
            throw EmailAlreadyExistException("Expert ${expert.email} already exists")
        }
            val expertRole = realmResource.roles().get("app_expert").toRepresentation()
            val passwordCred = CredentialRepresentation().apply {
                isTemporary = false
                type = CredentialRepresentation.PASSWORD
                value = expert.password
            }
            val attr: MutableMap<String, List<String>> = mutableMapOf();
            val fields: List<String> = expert.fields.split(",")
            attr["fields"] = fields

            val expertRep = UserRepresentation().apply {
                isEnabled = true
                username = expert.username
                firstName = expert.firstName
                lastName = expert.lastName
                email = expert.email
                credentials = listOf(passwordCred)
                attributes = attr
            }

            val response = userResource.create(expertRep)

            val userid = CreatedResponseUtil.getCreatedId(response)
            expert.id = userid
            if (expertRepository.existsById(userid)) {
                throw UserAlreadyExistException("Expert with id ${expert.id} already exist")
            } else {
                expertRepository.save(expert)
            }


            keycloak.realm("SpringBoot-Keycloak")
            //keycloak.realm("ticketingRealm")
                .users()
                .get(userid)
                .roles().realmLevel().add(listOf(expertRole))
            //println("response status ${response.status}, response info status ${response.statusInfo}\n reponse body ${response} and \n userid ${userid}")
            //println(customerRep.credentials[0].value)

    }





    override fun updateExpert(expert: Expert) {
        if (expertRepository.existsById(expert.id!!)) {
            val retrievedExpert = expertRepository.findById(expert.id!!)

            retrievedExpert?.username = expert.username
            retrievedExpert?.firstName = expert.firstName
            retrievedExpert?.lastName = expert.lastName
            retrievedExpert?.fields = expert.fields

            expertRepository.save(retrievedExpert!!)
        }
        else {
             throw UserNotFoundException("Expert with id ${expert.id} not found")
        }
    }

    override fun getTickets(id: String): Set<TicketDTO>? {
        if (expertRepository.existsById(id)) {
            val tickets = expertRepository.getTickets(id) ?: return null
            return tickets.map { it.toDTO() }.toSet()
        }
        else {
            throw UserNotFoundException("Expert not found")
        }
    }
}