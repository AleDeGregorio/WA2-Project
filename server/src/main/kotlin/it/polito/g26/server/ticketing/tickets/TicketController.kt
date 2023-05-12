package it.polito.g26.server.ticketing.tickets

import it.polito.g26.server.products.Product
import it.polito.g26.server.products.ProductDTO
import it.polito.g26.server.profiles.customer.Customer
import it.polito.g26.server.profiles.customer.CustomerDTO
import it.polito.g26.server.ticketing.chat.ChatDTO
import it.polito.g26.server.profiles.expert.Expert
import it.polito.g26.server.profiles.expert.ExpertDTO
import it.polito.g26.server.ticketing.statusTicket.StatusTicketDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat

@RestController
class TicketController(
    private val ticketService: TicketService
) {
    private fun expertDTOToEntity(expertDTO: ExpertDTO, id: Long?) : Expert {
        val expert = Expert(name = expertDTO.name!!, surname = expertDTO.surname!!, email = expertDTO.email!!)

        if (id != null) {
            expert.id = id
        }

        expert.fields = expertDTO.fields
        expert.email = expertDTO.email

        return expert
    }

    private fun customerDTOToEntity(customerDTO: CustomerDTO, email: String?) : Customer {
        val customer = Customer(name = customerDTO.name!!, surname = customerDTO.surname!!)

        customer.email = email ?: customerDTO.email
        customer.city = customerDTO.city
        customer.address = customerDTO.address

        return customer
    }

    private fun productDTOToEntity(deviceDTO: ProductDTO) : Product {
        val device = Product(deviceDTO.ean)

        device.name = deviceDTO.name
        device.category = deviceDTO.category
        device.brand = deviceDTO.brand
        device.price = deviceDTO.price

        return device
    }

    private fun ticketDTOToEntity(ticketDTO: TicketDTO) : Ticket {
        val ticket = Ticket()

        ticket.customer = customerDTOToEntity(ticketDTO.customer!!, null)
        ticket.expert = expertDTOToEntity(ticketDTO.expert!!, null)
        ticket.product = productDTOToEntity(ticketDTO.product!!)
        ticket.status = ticketDTO.status
        ticket.issueType = ticketDTO.issueType
        ticket.description = ticketDTO.description
        ticket.priorityLevel = ticketDTO.priorityLevel
        ticket.dateOfCreation = ticketDTO.dateOfCreation

        return ticket
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