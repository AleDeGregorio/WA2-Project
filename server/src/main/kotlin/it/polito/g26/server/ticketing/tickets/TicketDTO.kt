package it.polito.g26.server.ticketing.tickets

import it.polito.g26.server.products.ProductDTO
import it.polito.g26.server.products.toDTO
import it.polito.g26.server.ticketing.customers.CustomerDTO
import it.polito.g26.server.ticketing.customers.toDTO
import it.polito.g26.server.ticketing.experts.ExpertDTO
import it.polito.g26.server.ticketing.experts.toDTO
import it.polito.g26.server.ticketing.messages.MessageDTO
import it.polito.g26.server.ticketing.messages.toDTO
import it.polito.g26.server.ticketing.statuses.StatusDTO
import it.polito.g26.server.ticketing.statuses.toDTO

data class TicketDTO(
    val id: Long?,
    val typeOfIssue: String,
    val description: String,
    val product: ProductDTO?,
    val expertAssigned: ExpertDTO?,
    val status: List<StatusDTO>?,
    val customer: CustomerDTO?,
    //val chat: List<MessageDTO>
)

fun Ticket.toDTO(): TicketDTO {
    return TicketDTO(
        id,
        typeOfIssue,
        description,
        product?.toDTO(),
        expert?.toDTO(),
        status = this.status.map { it.toDTO() },
        customer?.toDTO()
        //chat = this.chat.map { it.toDTO() }
    )
}