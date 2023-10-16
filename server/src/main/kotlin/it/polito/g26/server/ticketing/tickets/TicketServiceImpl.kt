package it.polito.g26.server.ticketing.tickets

import it.polito.g26.server.*
import it.polito.g26.server.ticketing.chat.ChatDTO
import it.polito.g26.server.ticketing.chat.toDTO
import it.polito.g26.server.profiles.expert.Expert
import it.polito.g26.server.ticketing.statusTicket.*
import it.polito.g26.server.ticketing.utility.Status
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@Service
class TicketServiceImpl(
    private val ticketRepository: TicketRepository,
    private val statusTicketRepository: StatusTicketRepository
) : TicketService {
    override fun getAll(): List<TicketDTO> {
        return ticketRepository.findAll().map { it.toDTO() }
    }

    override fun getTicket(id: Long): TicketDTO? {
        println("trova questo")
        println(ticketRepository.findByIdOrNull(id))
        println("finito")
        return ticketRepository.findByIdOrNull(id)?.toDTO()
    }

    override fun getTicketByCustomer(customerId: String): List<TicketDTO>? {
        val list = ticketRepository.findByCustomer(customerId)?.map { it.toDTO() }
        if (list?.isEmpty()!!) {
            throw UserNotFoundException("Customer with id {$customerId} not found.")
        }
        return list
    }

    override fun getTicketByExpert(expertId: String): List<TicketDTO>? {
        return ticketRepository.findByExpert(expertId)?.map { it.toDTO() }
    }

    override fun getTicketByProduct(productId: Long): List<TicketDTO>? {
        return ticketRepository.findByProduct(productId)?.map { it.toDTO() }
    }

    override fun getTicketByDateOfCreation(dateOfCreation: Date): List<TicketDTO>? {
        return ticketRepository.findByDateOfCreation(dateOfCreation)?.map { it.toDTO() }
    }
/*
    override fun getStatusTicket(id: Long): Set<StatusTicketDTO>? {
        if (ticketRepository.existsById(id)) {
            val ticketStatus = ticketRepository.getStatusTicket(id) ?: return null
            return ticketStatus.map { it.toDTO() }.toSet()
        }
        else {
            throw TicketNotFoundException("Ticket with id $id not found!")
        }
    }
 */

/*
    override fun getChats(id: Long): Set<ChatDTO>? {
        if (ticketRepository.existsById(id)) {
            val chats = ticketRepository.getChats(id) ?: return null
            return chats.map { it.toDTO() }.toSet()
        }
        else {
            throw TicketNotFoundException("Ticket with id $id not found!")
        }
    }
 */
    override fun insertTicket(ticket: Ticket) {
        if (ticket.id != null && ticketRepository.existsById(ticket.id!!)) {
            throw TicketAlreadyExistException("Ticket with id ${ticket.id} already exists")
        }
        else if(ticket.customer == null){
            throw MissingUserException("No Customer was provided!")
        }else if(ticket.product == null){
            throw MissingProductException("No Product was provided!")
        }else{
            val savedTicket = ticketRepository.save(ticket)
            val ticketDate = TicketDate(savedTicket, savedTicket.dateOfCreation)
            val statusTicket = StatusTicket(ticketDate, Status.OPEN)
            statusTicketRepository.save(statusTicket)
        }
    }

    @Transactional
    override fun setPriorityLevel(id: Long, priorityLevel: Int) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.setPriorityLevel(id, priorityLevel)
        }
        else {
            throw TicketNotFoundException("Ticket with id $id not found!")
        }
    }

    @Transactional
    override fun setExpert(id: Long, expert: Expert) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.setExpert(id, expert)
        }
        else {
            throw TicketNotFoundException("Ticket with id $id not found!")
        }
    }
}