package it.polito.g26.server.ticketing.experts

import it.polito.g26.server.ticketing.managers.Manager
import it.polito.g26.server.ticketing.managers.ManagerDTO
import it.polito.g26.server.ticketing.tickets.Ticket
import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.users.Role
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany

data class ExpertDTO (
    val tickets: MutableSet<TicketDTO>,
    val role: Role,
    val field: String,
    val managers: MutableSet<ManagerDTO>
)

