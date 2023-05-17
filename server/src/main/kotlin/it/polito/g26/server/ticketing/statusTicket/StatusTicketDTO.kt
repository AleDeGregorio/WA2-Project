package it.polito.g26.server.ticketing.statusTicket

import it.polito.g26.server.profiles.customer.Customer
import it.polito.g26.server.profiles.customer.CustomerDTO
import it.polito.g26.server.profiles.customer.toEntity
import it.polito.g26.server.ticketing.utility.Status
import java.util.Date

data class StatusTicketDTO(
    val id: Long,
    val lastModifiedDate: Date,
    val status: Status
)

fun StatusTicket.toDTO() : StatusTicketDTO {
    return StatusTicketDTO(
        id = this.ticketDate?.id?.id!!,
        lastModifiedDate = this.ticketDate!!.lastModifiedDate!!,
        status = this.status!!
    )
}
