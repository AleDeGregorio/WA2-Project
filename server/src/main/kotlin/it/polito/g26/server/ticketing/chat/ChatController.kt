package it.polito.g26.server.ticketing.chat

import it.polito.g26.server.*
import it.polito.g26.server.ticketing.messages.MessageDTO
import it.polito.g26.server.ticketing.tickets.TicketDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat

@RestController
@RequestMapping("/chat")
class ChatController(
    private val chatService: ChatService
) {
    /*private fun chatDTOToEntity(chatDTO: ChatDTO) : Chat {
        val chat = Chat()
        chat.ticket = chatDTO.ticket
        chat.creationDate = chatDTO.creationDate
        return chat
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getChat(@PathVariable id: Long) : ChatDTO? {
        return chatService.getChat(id) ?: throw ChatNotFoundException("Chat with id $id not found!")
    }

    @GetMapping("/ticket/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    fun getChatByTicket(@PathVariable ticketId: Long) : List<ChatDTO>? {
        return chatService.getChatByTicket(ticketId) ?: throw TicketNotFoundException("Ticket with id $ticketId not found!")
    }

    @GetMapping("/date/{date}")
    @ResponseStatus(HttpStatus.OK)
    fun getChatByDate(@PathVariable date: String) : List<ChatDTO>? {
        val formattedDate = SimpleDateFormat("yyyy-MM-dd").parse(date)
        return chatService.getChatByDate(formattedDate) ?: throw ChatNotFoundException("No Chats created on the $date")
    }

    @GetMapping("/messages/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getChatMessages(@PathVariable id: Long) : Set<MessageDTO> {
        return chatService.getMessages(id) ?: throw ChatNotFoundException("Chat with id $id not found!")
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertChat(@RequestBody chatDTO: ChatDTO?) {
        if (chatDTO == null) {
            throw EmptyPostBodyException("Empty chat body")
        }else if(chatService.getChat(chatDTO.id!!)!=null) {
            throw ChatAlreadyExistsException("${chatDTO.id} already exists!")
        }else{
            val insertChat = chatDTOToEntity(chatDTO)
            chatService.insertChat(insertChat)
        }
    }*/
}