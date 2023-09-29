package it.polito.g26.server.ticketing.messages

import it.polito.g26.server.profiles.Profile
import it.polito.g26.server.ticketing.attachment.Attachment
import it.polito.g26.server.ticketing.chat.Chat
import it.polito.g26.server.ticketing.utility.Role
import java.time.LocalDateTime
import java.util.*

data class MessageDTO(
    val id: Long?,
    val chat: Chat?,
    val attachments: MutableSet<Attachment>,
    val sender: Profile?,
    val content: String,
    val timestamp: LocalDateTime?
)

fun Message.toDTO() : MessageDTO {
    return MessageDTO(id, chat, attachments, sender, content, timestamp)
}

fun MessageDTO.toEntity() : Message {
    return Message(id, chat, attachments, sender, content, timestamp)
}