package it.polito.g26.server.ticketing.chat

import it.polito.g26.server.ticketing.messages.Message
import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.ticketing.tickets.toDTO
import it.polito.g26.server.ticketing.tickets.toEntity
import java.util.*

data class ChatDTO(
    val id: Long?,
    val messages: MutableSet<Message>,
    val ticket: TicketDTO,
    val creationDate: Date?
)

fun Chat.toDTO() : ChatDTO {
    return ChatDTO(id, messages, ticket!!.toDTO(), creationDate)
}
fun ChatDTO.toEntity() : Chat {
    return Chat(id, messages, ticket.toEntity(), creationDate)
}