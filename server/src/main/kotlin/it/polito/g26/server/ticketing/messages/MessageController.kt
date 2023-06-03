package it.polito.g26.server.ticketing.messages

import it.polito.g26.server.*
import it.polito.g26.server.products.ProductDTO
import it.polito.g26.server.ticketing.attachment.AttachmentDTO
import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.ticketing.tickets.toDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/message")
class MessageController(
    private val messageService: MessageService
) {
    private fun messageDTOToEntity(messageDTO: MessageDTO): Message {
        val message = Message()

        message.chat = messageDTO.chat
        message.content = messageDTO.content
        message.sentBy = messageDTO.sentBy
        message.sendingDate = messageDTO.sendingDate

        return message
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getMessage(@PathVariable id: Long): MessageDTO? {
        return messageService.getMessage(id) ?: throw MessageNotFoundException("Message with id $id not found!")
    }

    @GetMapping("/chat/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    fun getMessageByChat(@PathVariable chatId: Long): List<MessageDTO>? {
        return messageService.getMessageByChat(chatId) ?: throw ChatNotFoundException("Chat with id $chatId not found!")
    }

    @GetMapping("/attachments/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getMessageAttachments(@PathVariable id: Long): Set<AttachmentDTO> {
        return messageService.getAttachments(id) ?: throw MessageNotFoundException("Message with id $id not found!")
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertMessage(@RequestBody messageDTO: MessageDTO?) {
        if (messageDTO == null) {
            throw EmptyPostBodyException("Empty message body")
        } else if (messageService.getMessage(messageDTO.id!!) != null) {
            throw MessageAlreadySentException("${messageDTO.id} already in use!")
        } else {
            val insertMessage = messageDTOToEntity(messageDTO)
            messageService.insertMessage(insertMessage)
        }
    }
}