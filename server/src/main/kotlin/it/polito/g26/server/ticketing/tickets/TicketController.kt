package it.polito.g26.server.ticketing.tickets

import org.springframework.web.bind.annotation.RestController

@RestController
class TicketController(
    private val ticketService: TicketService
) {

}