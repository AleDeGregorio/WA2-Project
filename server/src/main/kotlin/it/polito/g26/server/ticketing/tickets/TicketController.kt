package it.polito.g26.server.ticketing.tickets

import io.micrometer.observation.annotation.Observed
import it.polito.g26.server.*
import it.polito.g26.server.products.Product
import it.polito.g26.server.products.ProductDTO
import it.polito.g26.server.products.toEntity
import it.polito.g26.server.profiles.customer.Customer
import it.polito.g26.server.profiles.customer.CustomerDTO
import it.polito.g26.server.profiles.customer.toEntity
import it.polito.g26.server.ticketing.chat.ChatDTO
import it.polito.g26.server.profiles.expert.Expert
import it.polito.g26.server.profiles.expert.ExpertDTO
import it.polito.g26.server.profiles.expert.toEntity
import it.polito.g26.server.security.login.LoginController
import it.polito.g26.server.ticketing.statusTicket.StatusTicketDTO
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat

@RestController
@RequestMapping("/ticket")
@Observed
@Slf4j
class TicketController(
    private val ticketService: TicketService
) {
    private val log = LoggerFactory.getLogger(TicketController::class.java)

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun getAll() : List<TicketDTO> {
        val ticketL = ticketService.getAll()

        if(ticketL.isNotEmpty()) {
            log.info("Getting all tickets")
            return ticketL
        }
        else {
            log.info("Getting all tickets failed: no ticket in list")
            throw TicketListIsEmptyException("Ticket List is Empty")
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicket(@PathVariable id: Long) : TicketDTO? {
        val ticket = ticketService.getTicket(id)

        if (ticket != null) {
            log.info("Getting ticket $id")
            return ticket
        }
        else {
            log.info("Getting ticket $id failed: ticket not found")
            throw TicketNotFoundException("Ticket with id $id not found!")
        }
    }

    @GetMapping("/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketByCustomer(@PathVariable customerId: String) : List<TicketDTO>? {
        val tickets = ticketService.getTicketByCustomer(customerId)

        if (tickets != null) {
            log.info("Getting tickets for customer $customerId")
            return tickets
        }
        else {
            log.info("Getting tickets for customer $customerId failed: customer not found")
            throw UserNotFoundException("Customer with id $customerId not found!")
        }
    }

    @GetMapping("/expert/{expertId}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketByExpert(@PathVariable expertId: String) : List<TicketDTO>? {
        val tickets = ticketService.getTicketByExpert(expertId)

        if (tickets != null) {
            log.info("Getting tickets for expert $expertId")
            return tickets
        }
        else {
            log.info("Getting tickets for expert $expertId failed: expert not found")
            throw UserNotFoundException("Expert with id $expertId not found!")
        }
    }

    @GetMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketByProduct(@PathVariable productId: Long) : List<TicketDTO>? {
        val tickets = ticketService.getTicketByProduct(productId)

        if (tickets != null) {
            log.info("Getting ticket for product $productId")
            return tickets
        }
        else {
            log.info("Getting ticket for product $productId failed: product not found")
            throw ProductNotFoundException("Product with ean $productId not found!")
        }
    }

    @GetMapping("/date/{dateOfCreation}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketByDateOfCreation(@PathVariable dateOfCreation: String) : List<TicketDTO>? {
        val formattedDate = SimpleDateFormat("yyyy-MM-dd").parse(dateOfCreation)

        val tickets = ticketService.getTicketByDateOfCreation(formattedDate)

        if (tickets != null) {
            log.info("Getting tickets with date of creation $dateOfCreation")
            return tickets
        }
        else {
            log.info("Getting tickets with date of creation $dateOfCreation failed: no ticket found")
            throw TicketNotFoundException("No Tickets created on the $dateOfCreation")
        }
    }

    /*@GetMapping("/API/ticket/statusTicket/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getStatusTicket(@PathVariable id: Long) : Set<StatusTicketDTO>? {
        return ticketService.getStatusTicket(id) ?: throw TicketNotFoundException("Ticket with id $id not found!")
    }

    @GetMapping("/API/ticket/chats/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getChats(@PathVariable id: Long) : Set<ChatDTO>? {
        return ticketService.getChats(id) ?: throw TicketNotFoundException("Ticket with id $id not found!")
    }

     */

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertTicket(@RequestBody ticketDTO: TicketDTO?) {
        if (ticketDTO == null) {
            log.info("Inserting new ticket failed: empty body")
            throw EmptyPostBodyException("Empty Ticket body")
        }
        else {
            log.info("Inserting new ticket")
            ticketService.insertTicket(ticketDTO.toEntity())
        }
    }

    @PutMapping("/{id}/priority")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun setPriorityLevel(@PathVariable id: Long, @RequestParam priorityLevel: Int?) {
        if (ticketService.getTicket(id) == null) {
            log.info("Setting priority level $priorityLevel to ticket $id failed: ticket not found")
            throw TicketNotFoundException("Ticket with id $id not found!")
        }
        else if (priorityLevel != null) {
            log.info("Setting priority level $priorityLevel to ticket $id")
            ticketService.setPriorityLevel(id, priorityLevel)
        }
        else {
            log.info("Setting priority level to ticket $id failed: no priority level")
            throw EmptyPostBodyException("Value not found for Priority Level")
        }
    }

    @PutMapping("/{id}/expert")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun setExpert(@PathVariable id: Long, @RequestBody expertDTO: ExpertDTO?) {
        if (ticketService.getTicket(id) == null) {
            log.info("Setting expert to ticket $id failed: ticket not found")
            throw TicketNotFoundException("Ticket with id $id not found!")
        }
        else if (expertDTO != null) {
            log.info("Setting expert ${expertDTO.email} to ticket $id")
            ticketService.setExpert(id, expertDTO.toEntity())
        }
        else {
            log.info("Setting expert to ticket $id failed: expert not found")
            throw UserNotFoundException("Expert with id $id not found!")
        }
    }
}