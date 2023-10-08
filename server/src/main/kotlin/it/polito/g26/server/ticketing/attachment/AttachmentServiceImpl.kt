package it.polito.g26.server.ticketing.attachment

import it.polito.g26.server.AttachmentAlreadyInsertedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AttachmentServiceImpl(
    private val attachmentRepository: AttachmentRepository
) : AttachmentService {
    override fun getAttachment(id: Long): AttachmentDTO? {
        return attachmentRepository.findByIdOrNull(id)?.toDTO()
    }

    override fun getAttachmentByMessage(messageId: Long): List<AttachmentDTO>? {
        return attachmentRepository.findByMessage(messageId)?.map { it.toDTO() }
    }

    override fun insertAttachment(attachment: Attachment) {
        println("entro nel servizio di inserzione")
        if (attachment.id != null && attachmentRepository.existsById(attachment.id!!)) {
            throw AttachmentAlreadyInsertedException("Attachment with id ${attachment.id} already exists")
        }
        else {
            attachmentRepository.save(attachment)
        }
    }
}