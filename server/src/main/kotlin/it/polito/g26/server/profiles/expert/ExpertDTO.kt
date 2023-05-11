package it.polito.g26.server.profiles.expert

import it.polito.g26.server.ticketing.ticket.Ticket
import kotlin.math.exp

data class ExpertDTO(
    val id: Long?,
    val name: String?,
    val surname: String?,
    val email: String?,
    val fields: String,
    val tickets: MutableSet<Ticket>
)

fun Expert.toDTO() : ExpertDTO {
    return ExpertDTO(id, name, surname, email, fields, tickets)
}

