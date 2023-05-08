package it.polito.g26.server.ticketing.tickets

import it.polito.g26.server.ticketing.statuses.StatusDTO
import it.polito.g26.server.ticketing.statuses.toDTO
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TicketServiceImpl(
    private val ticketRepository: TicketRepository
): TicketService {
    override fun getAll(): List<TicketDTO> {
        return ticketRepository.findAll().map { it.toDTO() }
    }

    override fun getTicket(id: Long): TicketDTO? {
        return ticketRepository.findByIdOrNull(id)?.toDTO()
    }

    override fun getTicketByCustomer(customerId: String): List<TicketDTO>? {
        return ticketRepository.findByCustomer(customerId)?.map { it.toDTO() }
    }

    override fun getTicketByExpert(expertId: String): List<TicketDTO>? {
        return ticketRepository.findByExpert(expertId)?.map { it.toDTO() }
    }

    override fun getTicketByProduct(productId: Long): List<TicketDTO>? {
        return ticketRepository.findByProduct(productId)?.map { it.toDTO() }
    }

    override fun getTicketByDateOfCreation(dateOfCreation: LocalDate): List<TicketDTO>? {
        return ticketRepository.findByDateOfCreation(dateOfCreation)?.map { it.toDTO() }
    }

    override fun getStatusTicket(id: Long): Set<StatusDTO>? {
        if (ticketRepository.existsById(id)) {
            return ticketRepository.getStatusTicket(id)?.map { it.toDTO() }?.toSet()
        }
        else {
            throw Exception("Ticket not found")
        }
    }

    override fun insertTicket(ticket: Ticket) {
        if(ticketRepository.existsById(ticket.id!!)) {
            throw Exception("Ticket already inserted")
        }
        else{
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
}