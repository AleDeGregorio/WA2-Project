package it.polito.g26.server.profiles.expert

import it.polito.g26.server.profiles.customer.Customer
import it.polito.g26.server.ticketing.tickets.Ticket

data class ExpertDTO(
    val id: String?,
    val username: String,
    val firstName: String,
    val lastName: String,
    val password: String?,
    val email: String,
    val fields: String
)

fun Expert.toDTO() : ExpertDTO {
    return ExpertDTO(id,  username, firstName, lastName, password, email, fields)
}

fun ExpertDTO.toEntity(): Expert {
    return Expert(id = id, username = username,
        firstName = firstName, lastName = lastName, email =  email,  password = password ?: "password", fields =  fields)

}