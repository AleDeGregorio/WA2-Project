package it.polito.g26.server.profiles.expert

import it.polito.g26.server.ticketing.tickets.TicketDTO

interface ExpertService {
    fun getAll() : List<ExpertDTO>
    fun getExpert(email: String) : ExpertDTO?

    fun getExpertsByField(field: String) : List<ExpertDTO>

    fun insertExpert(expert: Expert)

    fun updateExpert(expert: Expert)

    fun getTickets(id: String) : Set<TicketDTO>?
}