package it.polito.g26.server.profiles.customer

import it.polito.g26.server.ticketing.ticket.Ticket
import it.polito.g26.server.ticketing.utility.Role

data class CustomerDTO (
    val id: Long?,
    val name: String?,
    val surname: String?,
    val role: Role,
    val email: String,
    val city: String,
    val address: String,
    val tickets: MutableSet<Ticket>
)

fun Customer.toDTO() : CustomerDTO {
    return CustomerDTO(id, name, surname, role, email, city, address, tickets)
}