package it.polito.g26.server.ticketing.managers

import it.polito.g26.server.ticketing.experts.Expert
import it.polito.g26.server.ticketing.experts.ExpertDTO
import it.polito.g26.server.ticketing.tickets.Ticket
import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.users.Role
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany

data class ManagerDTO (
    val experts: MutableSet<ExpertDTO>,
    val tickets: MutableSet<TicketDTO>,
    val role: Role
)