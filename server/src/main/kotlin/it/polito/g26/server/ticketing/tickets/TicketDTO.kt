package it.polito.g26.server.ticketing.tickets

import it.polito.g26.server.products.Product
import it.polito.g26.server.products.ProductDTO
import it.polito.g26.server.products.toDTO
import it.polito.g26.server.ticketing.chat.Chat
import it.polito.g26.server.profiles.customer.Customer
import it.polito.g26.server.profiles.customer.CustomerDTO
import it.polito.g26.server.profiles.customer.toDTO
import it.polito.g26.server.profiles.expert.Expert
import it.polito.g26.server.profiles.expert.ExpertDTO
import it.polito.g26.server.profiles.expert.toDTO
import it.polito.g26.server.ticketing.statusTicket.StatusTicket
import java.util.Date

data class TicketDTO(
    val id: Long?,
    val customer: CustomerDTO?,
    val expert: ExpertDTO?,
    val product: ProductDTO?,
    val status: MutableSet<StatusTicket>,
    val chats: MutableSet<Chat>,
    val issueType: String,
    val description: String,
    val priorityLevel: Int?,
    val dateOfCreation: Date?
)

fun Ticket.toDTO() : TicketDTO {
    return TicketDTO(id, customer?.toDTO(), expert?.toDTO(), product?.toDTO(), status, chats, issueType, description, priorityLevel, dateOfCreation)
}
