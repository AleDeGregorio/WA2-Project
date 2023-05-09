package it.polito.g26.server.ticketing.ticket

import it.polito.g26.server.ticketing.chat.ChatDTO
import it.polito.g26.server.ticketing.expert.Expert
import it.polito.g26.server.ticketing.expert.ExpertDTO
import it.polito.g26.server.ticketing.statusTicket.StatusTicketDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat

@RestController
class TicketController(
    private val ticketService: TicketService
) {
    private fun ticketDTOToEntity(ticketDTO: TicketDTO) : Ticket {
        val ticket = Ticket()

        ticket.customer = ticketDTO.customer
        ticket.expert = ticketDTO.expert
        ticket.product = ticketDTO.product
        ticket.status = ticketDTO.status
        ticket.issueType = ticketDTO.issueType
        ticket.description = ticketDTO.description
        ticket.priorityLevel = ticketDTO.priorityLevel
        ticket.dateOfCreation = ticketDTO.dateOfCreation

        return ticket
    }

    private fun expertDTOToEntity(expertDTO: ExpertDTO, id: Long?) : Expert {
        val expert = Expert()

        if (id != null) {
            expert.id = id
        }

        expert.name = expertDTO.name
        expert.surname = expertDTO.surname
        expert.fields = expertDTO.fields

        return expert
    }

    @GetMapping("/API/tickets")
    @ResponseStatus(HttpStatus.OK)
    fun getAll() : List<TicketDTO> {
        return ticketService.getAll()
    }

    @GetMapping("/API/ticket/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicket(@PathVariable id: Long) : TicketDTO? {
        return ticketService.getTicket(id) ?: throw Exception("Ticket not found")
    }

    @GetMapping("/API/ticket/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketByCustomer(@PathVariable customerId: Long) : List<TicketDTO>? {
        return ticketService.getTicketByCustomer(customerId) ?: throw Exception("Customer not found")
    }

    @GetMapping("/API/ticket/expert/{expertId}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketByExpert(@PathVariable expertId: Long) : List<TicketDTO>? {
        return ticketService.getTicketByExpert(expertId) ?: throw Exception("Expert not found")
    }

    @GetMapping("/API/ticket/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketByProduct(@PathVariable productId: Long) : List<TicketDTO>? {
        return ticketService.getTicketByProduct(productId) ?: throw Exception("Product not found")
    }

    @GetMapping("/API/ticket/date/{dateOfCreation}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketByDateOfCreation(@PathVariable dateOfCreation: String) : List<TicketDTO>? {
        val formattedDate = SimpleDateFormat("yyyy-MM-dd").parse(dateOfCreation)
        return ticketService.getTicketByDateOfCreation(formattedDate) ?: throw Exception("Ticket not found")
    }

    @GetMapping("/API/ticket/statusTicket/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getStatusTicket(@PathVariable id: Long) : Set<StatusTicketDTO>? {
        return ticketService.getStatusTicket(id) ?: throw Exception("Ticket not found")
    }

    @GetMapping("/API/ticket/chats/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getChats(@PathVariable id: Long) : Set<ChatDTO>? {
        return ticketService.getChats(id) ?: throw Exception("Ticket not found")
    }

    @PostMapping("/API/ticket")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertTicket(@RequestBody ticketDTO: TicketDTO?) {
        if (ticketDTO != null) {
            val insertTicket = ticketDTOToEntity(ticketDTO)

            ticketService.insertTicket(insertTicket)
        }
        else {
            throw Exception("Empty ticket body")
        }
    }

    @PutMapping("/API/ticket/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun setPriorityLevel(@PathVariable id: Long, @RequestParam priorityLevel: Int?) {
        if (priorityLevel != null) {
            ticketService.setPriorityLevel(id, priorityLevel)
        }
        else {
            throw Exception("Empty priority level")
        }
    }

    @PutMapping("/API/ticket/expert/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun setExpert(@PathVariable id: Long, @RequestParam expertDTO: ExpertDTO?) {
        if (expertDTO != null) {
            val setExpert = expertDTOToEntity(expertDTO, expertDTO.id)

            ticketService.setExpert(id, setExpert)
        }
        else {
            throw Exception("Empty expert body")
        }
    }
}