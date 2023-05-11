package it.polito.g26.server.profiles.expert

import it.polito.g26.server.ticketing.ticket.TicketDTO

interface ExpertService {
    fun getExpert(id: Long) : ExpertDTO?

    fun getExpertsByField(field: String) : List<ExpertDTO>?

    fun insertExpert(expert: Expert)

    fun updateExpert(expert: Expert)

    fun getTickets(id: Long) : Set<TicketDTO>?
}