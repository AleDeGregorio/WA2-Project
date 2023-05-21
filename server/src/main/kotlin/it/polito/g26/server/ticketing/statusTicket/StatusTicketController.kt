package it.polito.g26.server.ticketing.statusTicket

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/status")
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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getStatusTicket(@PathVariable id: Long) : List<StatusTicketDTO>? {
        return statusTicketService.getStatusTicket(id) ?: throw Exception("Ticket not found")
    }

    @GetMapping("/latest/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getLatestStatus(@PathVariable id: Long) : StatusTicketDTO? {
        return statusTicketService.getLatestStatus(id) ?: throw Exception("Ticket not found")
    }

    @PostMapping("")
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