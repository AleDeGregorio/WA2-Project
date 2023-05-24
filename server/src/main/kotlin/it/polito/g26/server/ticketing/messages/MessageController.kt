package it.polito.g26.server.ticketing.messages

import it.polito.g26.server.ticketing.attachment.AttachmentDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/message")
class MessageController(
    private val messageService: MessageService
) {
    private fun messageDTOToEntity(messageDTO: MessageDTO) : Message {
        val message = Message()

        message.chat = messageDTO.chat
        message.content = messageDTO.content
        message.sentBy = messageDTO.sentBy
        message.sendingDate = messageDTO.sendingDate

        return message
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getMessage(@PathVariable id: Long) : MessageDTO? {
        return messageService.getMessage(id) ?: throw Exception("Message not found")
    }

    @GetMapping("/chat/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    fun getMessageByChat(@PathVariable chatId: Long) : List<MessageDTO>? {
        return messageService.getMessageByChat(chatId) ?: throw Exception("Chat not found")
    }

    @GetMapping("/attachments/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getMessageAttachments(@PathVariable id: Long) : Set<AttachmentDTO> {
        return messageService.getAttachments(id) ?: throw Exception("Message not found")
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertMessage(@RequestBody messageDTO: MessageDTO?) {
        if (messageDTO != null) {
            val insertMessage = messageDTOToEntity(messageDTO)

            messageService.insertMessage(insertMessage)
        }
        else {
            throw Exception("Empty message body")
        }
    }
}