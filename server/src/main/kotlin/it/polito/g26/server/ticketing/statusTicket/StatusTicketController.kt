package it.polito.g26.server.ticketing.statusTicket

import it.polito.g26.server.EmptyPostBodyException
import it.polito.g26.server.TicketNotFoundException
import it.polito.g26.server.products.ProductDTO
import it.polito.g26.server.ticketing.tickets.toEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class StatusTicketController(
    @Autowired
    private val statusTicketService: StatusTicketService
) {

    @GetMapping("/API/statusTicket/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getStatusTicket(@PathVariable id: Long) : List<StatusTicketDTO>? {
        return statusTicketService.getStatusTicket(id) ?: throw TicketNotFoundException("Ticket with id $id not found!")
    }

    @GetMapping("/API/statusTicket/latest/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getLatestStatus(@PathVariable id: Long) : StatusTicketDTO? {
        return statusTicketService.getLatestStatus(id) ?: throw TicketNotFoundException("Ticket with id $id not found!")
    }

    @PostMapping("/API/statusTicket/open")
    @ResponseStatus(HttpStatus.CREATED)
    fun openStatusTicket(@RequestBody statusTicketDTO: StatusTicketDTO?) {
        if (statusTicketDTO != null) {
            statusTicketService.openStatusTicket(statusTicketDTO.toEntity())
        }
        else {
            throw EmptyPostBodyException("Empty Status Ticket body")
        }
    }
    @PostMapping("/API/statusTicket/close")
    @ResponseStatus(HttpStatus.CREATED)
    fun closeStatusTicket(@RequestBody statusTicketDTO: StatusTicketDTO?) {
        if (statusTicketDTO != null) {
            statusTicketService.closeStatusTicket(statusTicketDTO.toEntity())
        }
        else {
            throw EmptyPostBodyException("Empty Status Ticket body")
        }
    }
    @PostMapping("/API/statusTicket/progress")
    @ResponseStatus(HttpStatus.CREATED)
    fun progressStatusTicket(@RequestBody statusTicketDTO: StatusTicketDTO?) {
        if (statusTicketDTO != null) {
            statusTicketService.progressStatusTicket(statusTicketDTO.toEntity())
        }
        else {
            throw EmptyPostBodyException("Empty Status Ticket body")
        }
    }
    @PostMapping("/API/statusTicket/reopen")
    @ResponseStatus(HttpStatus.CREATED)
    fun reopenStatusTicket(@RequestBody statusTicketDTO: StatusTicketDTO?) {
        if (statusTicketDTO != null) {
            statusTicketService.reopenStatusTicket(statusTicketDTO.toEntity())
        }
        else {
            throw EmptyPostBodyException("Empty Status Ticket body")
        }
    }
    @PostMapping("/API/statusTicket/resolve")
    @ResponseStatus(HttpStatus.CREATED)
    fun resolveStatusTicket(@RequestBody statusTicketDTO: StatusTicketDTO?) {
        if (statusTicketDTO != null) {
            statusTicketService.resolveStatusTicket(statusTicketDTO.toEntity())
        }
        else {
            throw EmptyPostBodyException("Empty Status Ticket body")
        }
    }

}