package it.polito.g26.server.ticketing.statusTicket

import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.ticketing.tickets.toDTO
import it.polito.g26.server.ticketing.tickets.toEntity
import it.polito.g26.server.ticketing.utility.Status
import java.util.Date

data class StatusTicketDTO(
    val id: TicketDTO?,
    val lastModifiedDate: Date,
    val status: Status?
)

fun StatusTicket.toDTO() : StatusTicketDTO {
    return StatusTicketDTO(
        id = this.ticketDate?.id?.toDTO(),
        lastModifiedDate = this.ticketDate!!.lastModifiedDate!!,
        status = this.status!!
    )
}

fun StatusTicketDTO.toEntity() : StatusTicket {
    val td = TicketDate(id = this.id?.toEntity(),
    lastModifiedDate = this.lastModifiedDate)
    return StatusTicket(
        td,
        status = this.status
    )
}