package it.polito.g26.server.ticketing.expert

import it.polito.g26.server.ticketing.ticket.Ticket

data class ExpertDTO(
    val id: Long?,
    val name: String?,
    val surname: String?,
    val fields: String,
    val tickets: MutableSet<Ticket>
)

fun Expert.toDTO() : ExpertDTO {
    return ExpertDTO(id, name, surname, fields, tickets)
}
