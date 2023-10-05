package it.polito.g26.server.ticketing.messages

import it.polito.g26.server.*
import it.polito.g26.server.products.ProductDTO
import it.polito.g26.server.ticketing.attachment.AttachmentDTO
import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.ticketing.tickets.toDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/ticket/chat")
class MessageController(
    private val messageService: MessageService,

    @Autowired
    private val messageTemplate: SimpMessagingTemplate

) {

    @MessageMapping("/private-msg")
    fun sendPrivateMessage(@Payload msg: StompMsg): StompMsg {
        messageTemplate.convertAndSendToUser(msg.chatId, "/private", msg) // /user/CHATID/private
        return msg;
    }

    /*@GetMapping("/message/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getMessage(@PathVariable id: Long): MessageDTO? {
        return messageService.getMessage(id) ?: throw MessageNotFoundException("Message with id $id not found!")
    }

    @GetMapping("/{chatId}/message")
    @ResponseStatus(HttpStatus.OK)
    fun getMessageByChat(@PathVariable chatId: Long): List<MessageDTO>? {
        return messageService.getMessageByChat(chatId) ?: throw ChatNotFoundException("Chat with id $chatId not found!")
    }

    @GetMapping("/message/attachments/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getMessageAttachments(@PathVariable id: Long): Set<AttachmentDTO> {
        return messageService.getAttachments(id) ?: throw MessageNotFoundException("Message with id $id not found!")
    }

    @PostMapping("/message")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertMessage(@RequestBody messageDTO: MessageDTO?) {
        if (messageDTO == null) {
            throw EmptyPostBodyException("Empty message body")
        } else if (messageService.getMessage(messageDTO.id!!) != null) {
            throw MessageAlreadySentException("${messageDTO.id} already in use!")
        } else {
            messageService.insertMessage(messageDTO.toEntity())
        }
    }*/
}