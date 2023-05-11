package it.polito.g26.server.ticketing.chat

import it.polito.g26.server.ticketing.message.Message
import it.polito.g26.server.ticketing.ticket.Ticket
import java.util.*

data class ChatDTO(
    val id: Long?,
    val messages: MutableSet<Message>,
    val ticket: Ticket?,
    val creationDate: Date?
)

fun Chat.toDTO() : ChatDTO {
    return ChatDTO(id, messages, ticket, creationDate)
}