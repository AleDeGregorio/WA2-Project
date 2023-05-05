package it.polito.g26.server.ticketing.expert

import it.polito.g26.server.ticketing.ticket.TicketDTO

interface ExpertService {
    fun getExpert(id: Long) : ExpertDTO?

    fun insertExpert(expert: Expert)

    fun updateExpert(expert: Expert)

    fun getTickets(id: Long) : Set<TicketDTO>?
}