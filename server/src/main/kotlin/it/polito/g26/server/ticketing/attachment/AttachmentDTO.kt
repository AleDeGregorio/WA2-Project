package it.polito.g26.server.ticketing.attachment

import it.polito.g26.server.ticketing.message.Message

data class AttachmentDTO (
    val id: Long?,
    val message: Message?,
    val name: String,
    val content: String,
    val size: Int?
)

fun Attachment.toDTO() : AttachmentDTO {
    return AttachmentDTO(id, message, name, content, size)
}