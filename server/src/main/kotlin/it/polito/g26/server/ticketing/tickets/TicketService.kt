package it.polito.g26.server.ticketing.tickets

import it.polito.g26.server.ticketing.chat.ChatDTO
import it.polito.g26.server.profiles.expert.Expert
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

    fun getChats(id: Long) : Set<ChatDTO>?

    fun insertTicket(ticket: Ticket)

    fun setPriorityLevel(id: Long, priorityLevel: Int)

    fun setExpert(id: Long, expert: Expert)
}