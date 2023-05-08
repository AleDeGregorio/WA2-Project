package it.polito.g26.server.ticketing.messages

import it.polito.g26.server.ticketing.tickets.Ticket
import it.polito.g26.server.users.User
import jakarta.persistence.ManyToOne

data class MessageDTO(
    val messageId: Long?,
    val ticket: Ticket?,
    val sender: User?,
    val receiver: User?,
    val attachment: String
)