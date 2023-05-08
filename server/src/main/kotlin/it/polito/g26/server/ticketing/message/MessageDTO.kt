package it.polito.g26.server.ticketing.message

import it.polito.g26.server.ticketing.chat.Chat
import it.polito.g26.server.ticketing.utility.Role
import java.util.*

data class MessageDTO(
    val id: Long?,
    val chat: Chat?,
    val sentBy: Role?,
    val content: String,
    val sendingDate: Date?
)

fun Message.toDTO() : MessageDTO {
    return MessageDTO(id, chat, sentBy, content, sendingDate)
}