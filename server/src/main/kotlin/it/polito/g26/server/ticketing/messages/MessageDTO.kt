package it.polito.g26.server.ticketing.messages

import it.polito.g26.server.ticketing.attachment.Attachment
import it.polito.g26.server.ticketing.attachment.AttachmentDTO
import it.polito.g26.server.ticketing.attachment.toDTO
import it.polito.g26.server.ticketing.attachment.toEntity
import it.polito.g26.server.ticketing.chat.Chat
import it.polito.g26.server.ticketing.chat.ChatDTO
import it.polito.g26.server.ticketing.chat.toDTO
import it.polito.g26.server.ticketing.chat.toEntity
import it.polito.g26.server.ticketing.tickets.toDTO
import it.polito.g26.server.ticketing.utility.Role
import java.util.*

data class MessageDTO(
    val id: Long?,
    val chat: ChatDTO,
    val attachments: MutableSet<AttachmentDTO>,
    val sentBy: String,
    val content: String,
    val sendingDate: Date?
)

fun Message.toDTO() : MessageDTO {
    val a :MutableSet<AttachmentDTO> = mutableSetOf()
    for(image in attachments){
        a.add(image.toDTO())
    }
    return MessageDTO(id, chat!!.toDTO(), a, sentBy, content, sendingDate)
}

fun MessageDTO.toEntity() : Message {
    val a :MutableSet<Attachment> = mutableSetOf()
    for(image in attachments){
        a.add(image.toEntity())
    }
    return Message(id, chat.toEntity(), a, sentBy, content, sendingDate)
}