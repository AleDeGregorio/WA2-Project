package it.polito.g26.server.profiles.expert

import it.polito.g26.server.profiles.customer.Customer
import it.polito.g26.server.ticketing.tickets.Ticket

data class ExpertDTO(
    val id: Long?,
    val name: String,
    val surname: String,
    val email: String,
    val fields: String
)

fun Expert.toDTO() : ExpertDTO {
    return ExpertDTO(id, name, surname, email, fields)
}

fun ExpertDTO.toEntity(): Expert {
    val expert = Expert(name = name, surname = surname, email =  email, fields =  fields)
    expert.id = id
    return expert
}