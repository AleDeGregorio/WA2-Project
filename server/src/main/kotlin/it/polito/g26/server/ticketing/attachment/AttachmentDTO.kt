package it.polito.g26.server.ticketing.attachment

import it.polito.g26.server.ticketing.messages.Message

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

fun AttachmentDTO.toEntity() : Attachment {
    return Attachment(id, message, name, content, size)
}