package it.polito.g26.server.ticketing.messages

import io.micrometer.observation.annotation.Observed
import it.polito.g26.server.*
import it.polito.g26.server.products.ProductDTO
import it.polito.g26.server.security.login.LoginController
import it.polito.g26.server.ticketing.attachment.AttachmentDTO
import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.ticketing.tickets.toDTO
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ticket/chat")
@Observed
@Slf4j
class MessageController(
    private val messageService: MessageService
) {
    private val log = LoggerFactory.getLogger(MessageController::class.java)

    @GetMapping("/message/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getMessage(@PathVariable id: Long): MessageDTO? {
        val message = messageService.getMessage(id)

        if (message != null) {
            log.info("Getting message $id")
            return message
        }
        else {
            log.info("Getting message $id failed: message not found")
            throw MessageNotFoundException("Message with id $id not found!")
        }
    }

    @GetMapping("/{chatId}/message")
    @ResponseStatus(HttpStatus.OK)
    fun getMessageByChat(@PathVariable chatId: Long): List<MessageDTO>? {
        val messages = messageService.getMessageByChat(chatId)

        if (messages != null) {
            log.info("Getting messages for chat $chatId")
            return messages
        }
        else {
            log.info("Getting messages for chat $chatId failed: chat not found")
            throw ChatNotFoundException("Chat with id $chatId not found!")
        }
    }

    @GetMapping("/message/attachments/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getMessageAttachments(@PathVariable id: Long): Set<AttachmentDTO> {
        val attachments = messageService.getAttachments(id)

        if (attachments != null) {
            log.info("Getting attachments for message $id")
            return attachments
        }
        else {
            log.info("Getting attachments for message $id failed: message not found")
            throw MessageNotFoundException("Message with id $id not found!")
        }
    }

    @PostMapping("/message")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertMessage(@RequestBody messageDTO: MessageDTO?) {
        if (messageDTO == null) {
            log.info("Inserting new message failed: empty body")
            throw EmptyPostBodyException("Empty message body")
        }
        else if (messageService.getMessage(messageDTO.id!!) != null) {
            log.info("Inserting new message failed: id already in use")
            throw MessageAlreadySentException("${messageDTO.id} already in use!")
        }
        else {
            log.info("Inserting new message")
            messageService.insertMessage(messageDTO.toEntity())
        }
    }
}