package it.polito.g26.server.ticketing.message

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
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

    @GetMapping("/API/message/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getMessage(@PathVariable id: Long) : MessageDTO? {
        return messageService.getMessage(id) ?: throw Exception("Message not found")
    }

    @GetMapping("/API/message/chat/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    fun getMessageByChat(@PathVariable chatId: Long) : List<MessageDTO>? {
        return messageService.getMessageByChat(chatId) ?: throw Exception("Chat not found")
    }

    @PostMapping("/API/message")
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