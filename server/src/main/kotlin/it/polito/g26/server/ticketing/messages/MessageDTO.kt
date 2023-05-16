package it.polito.g26.server.ticketing.messages

import it.polito.g26.server.ticketing.attachment.Attachment
import it.polito.g26.server.ticketing.chat.Chat
import it.polito.g26.server.ticketing.utility.Role
import java.util.*

data class MessageDTO(
    val id: Long?,
    val chat: Chat?,
    val attachments: MutableSet<Attachment>,
    val sentBy: Role?,
    val content: String,
    val sendingDate: Date?
)

fun Message.toDTO() : MessageDTO {
    return MessageDTO(id, chat, attachments, sentBy, content, sendingDate)
}