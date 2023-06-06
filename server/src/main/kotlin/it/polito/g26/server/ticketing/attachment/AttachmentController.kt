package it.polito.g26.server.ticketing.attachment

import io.micrometer.observation.annotation.Observed
import it.polito.g26.server.AttachmentNotFoundException
import it.polito.g26.server.EmptyPostBodyException
import it.polito.g26.server.MessageNotFoundException
import it.polito.g26.server.security.login.LoginController
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/attachment")
@Observed
@Slf4j
class AttachmentController(
    private val attachmentService: AttachmentService
) {
    private val log = LoggerFactory.getLogger(AttachmentController::class.java)

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getAttachment(@PathVariable id: Long) : AttachmentDTO? {
        val attachment = attachmentService.getAttachment(id)

        if (attachment != null) {
            log.info("Getting attachment $id")
            return attachment
        }
        else {
            log.info("Getting attachment $id failed: attachment not found")
            throw AttachmentNotFoundException("Attachment with id $id not found!")
        }
    }

    @GetMapping("/message/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    fun getAttachmentByMessage(@PathVariable messageId: Long) : List<AttachmentDTO>? {
        val attachments = attachmentService.getAttachmentByMessage(messageId)

        if (attachments != null) {
            log.info("Getting attachments for message $messageId")
            return attachments
        }
        else {
            log.info("Getting attachments for message $messageId failed: message not found")
            throw MessageNotFoundException("Message with id $messageId not found!")
        }
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertAttachment(@RequestBody attachmentDTO: AttachmentDTO?) {
        if (attachmentDTO != null) {
            log.info("Inserting new attachment")
            attachmentService.insertAttachment(attachmentDTO.toEntity())
        }
        else {
            log.info("Inserting new attachment failed: empty body")
            throw EmptyPostBodyException("Empty attachment body")
        }
    }
}