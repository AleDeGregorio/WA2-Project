package it.polito.g26.server.ticketing.tickets

import it.polito.g26.server.ticketing.statuses.StatusDTO
import java.time.LocalDate

interface TicketService {
    fun getAll() : List<TicketDTO>
    fun getTicket(id: Long) : TicketDTO?

    fun getTicketByCustomer(customerId: String) : List<TicketDTO>?
    fun getTicketByExpert(expertId: String) : List<TicketDTO>?

    fun getTicketByProduct(productId: Long) : List<TicketDTO>?
    fun getTicketByDateOfCreation(dateOfCreation: LocalDate) : List<TicketDTO>?

    fun getStatusTicket(id: Long) : Set<StatusDTO>?

    fun insertTicket(ticket: Ticket)

    fun setPriorityLevel(id: Long, priorityLevel: Int)
}