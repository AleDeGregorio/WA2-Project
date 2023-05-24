package it.polito.g26.server.ticketing.chat

import it.polito.g26.server.ticketing.messages.MessageDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat

@RestController
class ChatController(
    private val chatService: ChatService
) {
    @GetMapping("/API/chat/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getChat(@PathVariable id: Long) : ChatDTO? {
        return chatService.getChat(id) ?: throw Exception("Chat not found")
    }

    @GetMapping("/API/chat/ticket/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    fun getChatByTicket(@PathVariable ticketId: Long) : List<ChatDTO>? {
        return chatService.getChatByTicket(ticketId) ?: throw Exception("Ticket not found")
    }

    @GetMapping("/API/chat/date/{date}")
    @ResponseStatus(HttpStatus.OK)
    fun getChatByDate(@PathVariable date: String) : List<ChatDTO>? {
        val formattedDate = SimpleDateFormat("yyyy-MM-dd").parse(date)
        return chatService.getChatByDate(formattedDate) ?: throw Exception("Customer not found")
    }

    @GetMapping("/API/chat/messages/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getChatMessages(@PathVariable id: Long) : Set<MessageDTO> {
        return chatService.getMessages(id) ?: throw Exception("Chat not found")
    }

    @PostMapping("/API/chat")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertChat(@RequestBody chatDTO: ChatDTO?) {
        if (chatDTO != null) {
            chatService.insertChat(chatDTO.toEntity())
        }
        else {
            throw Exception("Empty chat body")
        }
    }
}