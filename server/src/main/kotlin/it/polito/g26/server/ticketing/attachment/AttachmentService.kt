package it.polito.g26.server.ticketing.attachment

interface AttachmentService {
    fun getAttachment(id: Long) : AttachmentDTO?

    fun getAttachmentByMessage(messageId: Long) : List<AttachmentDTO>?

    fun insertAttachment(attachment: Attachment)
}