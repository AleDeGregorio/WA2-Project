package it.polito.g26.server.ticketing.experts

import it.polito.g26.server.ticketing.tickets.Ticket
import it.polito.g26.server.ticketing.tickets.TicketDTO

interface ExpertService {
    fun getExpert(email: String): ExpertDTO?
    fun createExpert(expert: Expert)
    fun updateExpert(expert: Expert)
    fun openTicket(ticket: Ticket)
    fun getAssignedTickets(id: Long): List<TicketDTO>
    fun getExpertsByExpertises(expertises: String): List<ExpertDTO>
}