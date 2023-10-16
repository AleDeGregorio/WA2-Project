package it.polito.g26.server.ticketing.chat

import io.micrometer.observation.annotation.Observed
import it.polito.g26.server.*
import it.polito.g26.server.security.login.LoginController
import it.polito.g26.server.ticketing.messages.MessageDTO
import it.polito.g26.server.ticketing.tickets.TicketDTO
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import it.polito.g26.server.ticketing.tickets.toEntity
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
@Observed
@Slf4j
class ChatController(
    private val chatService: ChatService
) {
    private val log = LoggerFactory.getLogger(ChatController::class.java)

    private fun chatDTOToEntity(chatDTO: ChatDTO) : Chat {
        val chat = Chat()
        chat.ticket = chatDTO.ticket
        chat.creationDate = chatDTO.creationDate
        return chat
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getChat(@PathVariable id: Long) : ChatDTO? {
        val chat = chatService.getChat(id)

        if (chat != null) {
            log.info("Getting chat $id")
            return chat
        }
        else {
            log.info("Getting chat $id failed: chat not found")
            throw ChatNotFoundException("Chat with id $id not found!")
        }
    }

    @GetMapping("/ticket/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    fun getChatByTicket(@PathVariable ticketId: Long) : List<ChatDTO>? {
        val chats = chatService.getChatByTicket(ticketId)

        if (chats != null) {
            log.info("Getting chats for ticket $ticketId")
            return chats
        }
        else {
            log.info("Getting chats for ticket $ticketId failed: ticket not found")
            throw TicketNotFoundException("Ticket with id $ticketId not found!")
        }
    }

    @GetMapping("/date/{date}")
    @ResponseStatus(HttpStatus.OK)
    fun getChatByDate(@PathVariable date: String) : List<ChatDTO>? {
        val formattedDate = SimpleDateFormat("yyyy-MM-dd").parse(date)

        val chats = chatService.getChatByDate(formattedDate)

        if (chats != null) {
            log.info("Getting chats for date $date")
            return chats
        }
        else {
            log.info("Getting chats for date $date failed: chat not found")
            throw ChatNotFoundException("No Chats created on the $date")
        }
    }

    @GetMapping("/messages/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getChatMessages(@PathVariable id: Long) : Set<MessageDTO> {
        val messages = chatService.getMessages(id)

        if (messages != null) {
            log.info("Getting messages for chat $id")
            return messages
        }
        else {
            log.info("Getting messages for chat $id failed: chat not found")
            throw ChatNotFoundException("Chat with id $id not found!")
        }
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertChat(@RequestBody chatDTO: ChatDTO?): Long? {
        if (chatDTO == null) {
            log.info("Inserting new chat failed: empty body")
            throw EmptyPostBodyException("Empty chat body")
        }
        else if (chatDTO.id !=null && chatService.getChat(chatDTO.id)!=null) {
            log.info("Inserting new chat failed: chat already exists")
            throw ChatAlreadyExistsException("${chatDTO.id} already exists!")
        }
        else {
            log.info("Inserting new chat")
            val idGenerated=chatService.insertChat(chatDTO.toEntity())
            return idGenerated
        }
    }
}
