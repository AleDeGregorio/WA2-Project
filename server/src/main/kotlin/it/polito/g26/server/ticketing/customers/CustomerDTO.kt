package it.polito.g26.server.ticketing.customers

import it.polito.g26.server.ticketing.tickets.Ticket
import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.users.Role
import jakarta.persistence.OneToMany

data class CustomerDTO (
    val tickets: MutableSet<TicketDTO>,
    val role: Role
)