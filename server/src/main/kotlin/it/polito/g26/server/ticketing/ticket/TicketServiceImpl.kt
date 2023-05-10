package it.polito.g26.server.ticketing.ticket

import it.polito.g26.server.ticketing.chat.ChatDTO
import it.polito.g26.server.ticketing.chat.toDTO
import it.polito.g26.server.ticketing.expert.Expert
import it.polito.g26.server.ticketing.statusTicket.StatusTicketDTO
import it.polito.g26.server.ticketing.statusTicket.toDTO
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class TicketServiceImpl(
    private val ticketRepository: TicketRepository
) : TicketService {
    override fun getAll(): List<TicketDTO> {
        return ticketRepository.findAll().map { it.toDTO() }
    }

    override fun getTicket(id: Long): TicketDTO? {
        return ticketRepository.findByIdOrNull(id)?.toDTO()
    }

    override fun getTicketByCustomer(customerId: Long): List<TicketDTO>? {
        return ticketRepository.findByCustomer(customerId)?.map { it.toDTO() }
    }

    override fun getTicketByExpert(expertId: Long): List<TicketDTO>? {
        return ticketRepository.findByExpert(expertId)?.map { it.toDTO() }
    }

    override fun getTicketByProduct(productId: Long): List<TicketDTO>? {
        return ticketRepository.findByProduct(productId)?.map { it.toDTO() }
    }

    override fun getTicketByDateOfCreation(dateOfCreation: Date): List<TicketDTO>? {
        return ticketRepository.findByDateOfCreation(dateOfCreation)?.map { it.toDTO() }
    }

    override fun getStatusTicket(id: Long): Set<StatusTicketDTO>? {
        if (ticketRepository.existsById(id)) {
            val ticketStatus = ticketRepository.getStatusTicket(id) ?: return null
            return ticketStatus.map { it.toDTO() }.toSet()
        }
        else {
            throw Exception("Ticket not found")
        }
    }

    override fun getChats(id: Long): Set<ChatDTO>? {
        if (ticketRepository.existsById(id)) {
            val chats = ticketRepository.getChats(id) ?: return null
            return chats.map { it.toDTO() }.toSet()
        }
        else {
            throw Exception("Ticket not found")
        }
    }

    override fun insertTicket(ticket: Ticket) {
        if (ticket.id != null && ticketRepository.existsById(ticket.id!!)) {
            throw Exception("Ticket already inserted")
        }
        else {
            ticketRepository.save(ticket)
        }
    }

    override fun setPriorityLevel(id: Long, priorityLevel: Int) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.setPriorityLevel(id, priorityLevel)
        }
        else {
            throw Exception("Ticket not found")
        }
    }

    override fun setExpert(id: Long, expert: Expert) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.setExpert(id, expert)
        }
        else {
            throw Exception("Ticket not found")
        }
    }
}