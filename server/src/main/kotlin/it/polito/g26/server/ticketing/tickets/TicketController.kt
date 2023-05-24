package it.polito.g26.server.ticketing.tickets

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
import it.polito.g26.server.ticketing.statusTicket.StatusTicketDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat

@RestController
class TicketController(
    private val ticketService: TicketService
) {

    @GetMapping("/API/tickets")
    @ResponseStatus(HttpStatus.OK)
    fun getAll() : List<TicketDTO> {
        val ticketL = ticketService.getAll()
    if(ticketL.isNotEmpty())
    return ticketL
    else{
        throw TicketListIsEmptyException("Ticket List is Empty")
    }
}

    @GetMapping("/API/ticket/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicket(@PathVariable id: Long) : TicketDTO? {
        return ticketService.getTicket(id) ?: throw TicketNotFoundException("Ticket with id $id not found!")
    }

    @GetMapping("/API/ticket/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketByCustomer(@PathVariable customerId: Long) : List<TicketDTO>? {
        return ticketService.getTicketByCustomer(customerId) ?: throw UserNotFoundException("Customer with id $customerId not found!")
    }

    @GetMapping("/API/ticket/expert/{expertId}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketByExpert(@PathVariable expertId: Long) : List<TicketDTO>? {
        return ticketService.getTicketByExpert(expertId) ?: throw UserNotFoundException("Expert with id $expertId not found!")
    }

    @GetMapping("/API/ticket/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketByProduct(@PathVariable productId: Long) : List<TicketDTO>? {
        return ticketService.getTicketByProduct(productId) ?: throw ProductNotFoundException("Product with ean $productId not found!")
    }

    @GetMapping("/API/ticket/date/{dateOfCreation}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketByDateOfCreation(@PathVariable dateOfCreation: String) : List<TicketDTO>? {
        val formattedDate = SimpleDateFormat("yyyy-MM-dd").parse(dateOfCreation)
        return ticketService.getTicketByDateOfCreation(formattedDate) ?: throw TicketNotFoundException("No Tickets created on the $dateOfCreation")
    }

    @GetMapping("/API/ticket/statusTicket/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getStatusTicket(@PathVariable id: Long) : Set<StatusTicketDTO>? {
        return ticketService.getStatusTicket(id) ?: throw TicketNotFoundException("Ticket with id $id not found!")
    }

    @GetMapping("/API/ticket/chats/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getChats(@PathVariable id: Long) : Set<ChatDTO>? {
        return ticketService.getChats(id) ?: throw TicketNotFoundException("Ticket with id $id not found!")
    }

    @PostMapping("/API/ticket")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertTicket(@RequestBody ticketDTO: TicketDTO?) {
        if (ticketDTO == null) {
            throw EmptyPostBodyException("Empty Ticket body")
        }else{
            ticketService.insertTicket(ticketDTO.toEntity())
        }
    }

    @PutMapping("/API/ticket/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun setPriorityLevel(@PathVariable id: Long, @RequestParam priorityLevel: Int?) {
        if (ticketService.getTicket(id)==null)
        {
            throw TicketNotFoundException("Ticket with id $id not found!")
        }
        else if(priorityLevel != null)
        {
            ticketService.setPriorityLevel(id, priorityLevel)
        }
        else {
            throw EmptyPostBodyException("Value not found for Priority Level")
        }
    }

    @PutMapping("/API/ticket/expert/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun setExpert(@PathVariable id: Long, @RequestParam expertDTO: ExpertDTO?) {
        if (ticketService.getTicket(id)==null)
        {
            throw TicketNotFoundException("Ticket with id $id not found!")
        }
        else if(expertDTO != null)
        {
            ticketService.setExpert(id, expertDTO.toEntity())
        }
        else
        {
            throw UserNotFoundException("Expert with id $id not found!")
        }
    }
}