package it.polito.g26.server.ticketing.statusTicket

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class StatusTicketController(
    private val statusTicketService: StatusTicketService
) {
    private fun statusTicketDTOToEntity(statusTicketDTO: StatusTicketDTO) : StatusTicket {
        val ticketDate = TicketDate()

        ticketDate.id?.id = statusTicketDTO.id
        ticketDate.lastModifiedDate = statusTicketDTO.lastModifiedDate

        val statusTicket = StatusTicket()

        statusTicket.ticketDate = ticketDate
        statusTicket.status = statusTicketDTO.status

        return statusTicket
    }

    @GetMapping("/API/statusTicket/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getStatusTicket(@PathVariable id: Long) : List<StatusTicketDTO>? {
        return statusTicketService.getStatusTicket(id) ?: throw Exception("Ticket not found")
    }

    @GetMapping("/API/statusTicket/latest/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getLatestStatus(@PathVariable id: Long) : StatusTicketDTO? {
        return statusTicketService.getLatestStatus(id) ?: throw Exception("Ticket not found")
    }

    @PostMapping("/API/statusTicket")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertStatusTicket(statusTicketDTO: StatusTicketDTO?) {
        if (statusTicketDTO != null) {
            val insertStatusTicket = statusTicketDTOToEntity(statusTicketDTO)

            statusTicketService.insertStatusTicket(insertStatusTicket)
        }
        else {
            throw Exception("Empty status ticket body")
        }
    }
}