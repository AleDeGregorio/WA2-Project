package it.polito.g26.server.ticketing.attachment

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class AttachmentController(
    private val attachmentService: AttachmentService
) {
    @GetMapping("/API/attachment/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getAttachment(@PathVariable id: Long) : AttachmentDTO? {
        return attachmentService.getAttachment(id) ?: throw Exception("Attachment not found")
    }

    @GetMapping("/API/attachment/message/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    fun getAttachmentByMessage(@PathVariable messageId: Long) : List<AttachmentDTO>? {
        return attachmentService.getAttachmentByMessage(messageId) ?: throw Exception("Message not found")
    }

    @PostMapping("/API/attachment")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertAttachment(@RequestBody attachmentDTO: AttachmentDTO?) {
        if (attachmentDTO != null) {
            attachmentService.insertAttachment(attachmentDTO.toEntity())
        }
        else {
            throw Exception("Empty attachment body")
        }
    }
}