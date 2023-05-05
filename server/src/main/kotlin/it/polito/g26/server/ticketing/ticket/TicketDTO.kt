package it.polito.g26.server.ticketing.ticket

import it.polito.g26.server.ticketing.customer.Customer
import it.polito.g26.server.ticketing.device.Device
import it.polito.g26.server.ticketing.expert.Expert
import it.polito.g26.server.ticketing.statusTicket.StatusTicket
import java.util.Date

data class TicketDTO(
    val id: Long?,
    val customer: Customer?,
    val expert: Expert?,
    val product: Device?,
    val status: MutableSet<StatusTicket>,
    val issueType: String,
    val description: String,
    val priorityLevel: Int?,
    val dateOfCreation: Date?
)

fun Ticket.toDTO() : TicketDTO {
    return TicketDTO(id, customer, expert, product, status, issueType, description, priorityLevel, dateOfCreation)
}
