package it.polito.g26.server.ticketing.messages

import it.polito.g26.server.*
import it.polito.g26.server.products.ProductDTO
import it.polito.g26.server.ticketing.attachment.AttachmentDTO
import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.ticketing.tickets.toDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ticket/chat")
class MessageController(
    private val messageService: MessageService
) {
    @GetMapping("/message/{id}")
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
            println("sono nel if")
            println(messageDTO)
            throw EmptyPostBodyException("Empty message body")
        } else if (messageDTO.id !=null && messageService.getMessage(messageDTO.id) != null) {
            println("sono nell'else if")
            println(messageDTO)
            throw MessageAlreadySentException("${messageDTO.id} already in use!")
        } else {
            println("sono nell'else")
            println(messageDTO)
            messageService.insertMessage(messageDTO.toEntity())
        }
    }
}