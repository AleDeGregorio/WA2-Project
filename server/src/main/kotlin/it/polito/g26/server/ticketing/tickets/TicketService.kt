package it.polito.g26.server.ticketing.tickets

import it.polito.g26.server.ticketing.Status
import it.polito.g26.server.ticketing.experts.Expert
import it.polito.g26.server.ticketing.statuses.StatusDTO
import java.time.LocalDate
import java.util.UUID

interface TicketService {
    fun getAll() : List<TicketDTO>
    fun getTicket(id: Long) : TicketDTO?

    fun getTicketByCustomer(customerId: String) : List<TicketDTO>?
    fun getTicketByExpert(expertId: String) : List<TicketDTO>?

    fun getTicketByProduct(productId: Long) : List<TicketDTO>?
    fun getTicketByDateOfCreation(dateOfCreation: LocalDate) : List<TicketDTO>?

    fun getStatusTicket(id: Long) : Set<StatusDTO>?
    fun getTicketsByStatus(status: Status): List<TicketDTO>

    fun insertTicket(ticket: Ticket)

    fun assignExpertTicket(ticketId: Long, expertId: UUID)
    fun setPriorityLevel(id: Long, priorityLevel: Int)
}