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
    private fun statusTicketDTOToEntity(statusTicketDTO: StatusTicketDTO) : StatusTicket {
        val ticketDate = TicketDate()

        ticketDate.id = statusTicketDTO.id?.toEntity()
        ticketDate.lastModifiedDate = statusTicketDTO.lastModifiedDate

        val statusTicket = StatusTicket()

        statusTicket.ticketDate = ticketDate
        statusTicket.status = statusTicketDTO.status

        return statusTicket
    }

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
            val openedStatusTicket = statusTicketDTOToEntity(statusTicketDTO)
            statusTicketService.openStatusTicket(openedStatusTicket)
        }
        else {
            throw EmptyPostBodyException("Empty Status Ticket body")
        }
    }
    @PostMapping("/API/statusTicket/close")
    @ResponseStatus(HttpStatus.CREATED)
    fun closeStatusTicket(@RequestBody statusTicketDTO: StatusTicketDTO?) {
        if (statusTicketDTO != null) {
            val closedStatusTicket = statusTicketDTOToEntity(statusTicketDTO)

            statusTicketService.closeStatusTicket(closedStatusTicket)
        }
        else {
            throw EmptyPostBodyException("Empty Status Ticket body")
        }
    }
    @PostMapping("/API/statusTicket/progress")
    @ResponseStatus(HttpStatus.CREATED)
    fun progressStatusTicket(@RequestBody statusTicketDTO: StatusTicketDTO?) {
        if (statusTicketDTO != null) {
            val inProgressStatusTicket = statusTicketDTOToEntity(statusTicketDTO)

            statusTicketService.progressStatusTicket(inProgressStatusTicket)
        }
        else {
            throw EmptyPostBodyException("Empty Status Ticket body")
        }
    }
    @PostMapping("/API/statusTicket/reopen")
    @ResponseStatus(HttpStatus.CREATED)
    fun reopenStatusTicket(@RequestBody statusTicketDTO: StatusTicketDTO?) {
        if (statusTicketDTO != null) {
            val reopenedStatusTicket = statusTicketDTOToEntity(statusTicketDTO)

            statusTicketService.reopenStatusTicket(reopenedStatusTicket)
        }
        else {
            throw EmptyPostBodyException("Empty Status Ticket body")
        }
    }
    @PostMapping("/API/statusTicket/resolve")
    @ResponseStatus(HttpStatus.CREATED)
    fun resolveStatusTicket(@RequestBody statusTicketDTO: StatusTicketDTO?) {
        if (statusTicketDTO != null) {
            val resolvedStatusTicket = statusTicketDTOToEntity(statusTicketDTO)

            statusTicketService.resolveStatusTicket(resolvedStatusTicket)
        }
        else {
            throw EmptyPostBodyException("Empty Status Ticket body")
        }
    }

}