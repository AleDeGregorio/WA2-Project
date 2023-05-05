package it.polito.g26.server.ticketing.ticket

import it.polito.g26.server.ticketing.statusTicket.StatusTicketDTO
import java.util.Date

interface TicketService {
    fun getAll() : List<TicketDTO>

    fun getTicket(id: Long) : TicketDTO?

    fun getTicketByCustomer(customerId: Long) : List<TicketDTO>?

    fun getTicketByExpert(expertId: Long) : List<TicketDTO>?

    fun getTicketByProduct(productId: Long) : List<TicketDTO>?

    fun getTicketByDateOfCreation(dateOfCreation: Date) : List<TicketDTO>?

    fun getStatusTicket(id: Long) : Set<StatusTicketDTO>?

    fun insertTicket(ticket: Ticket)

    fun setPriorityLevel(id: Long, priorityLevel: Int)
}