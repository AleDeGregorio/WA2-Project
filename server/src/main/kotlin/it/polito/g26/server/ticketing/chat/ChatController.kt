package it.polito.g26.server.ticketing.chat

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
    private fun chatDTOToEntity(chatDTO: ChatDTO) : Chat {
        val chat = Chat()

        chat.customer = chatDTO.customer
        chat.expert = chatDTO.expert
        chat.product = chatDTO.product
        chat.creationDate = chatDTO.creationDate

        return chat
    }

    @GetMapping("/API/chat/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getChat(@PathVariable id: Long) : ChatDTO? {
        return chatService.getChat(id) ?: throw Exception("Chat not found")
    }

    @GetMapping("/API/chat/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    fun getChatByCustomer(@PathVariable customerId: Long) : List<ChatDTO>? {
        return chatService.getChatByCustomer(customerId) ?: throw Exception("Customer not found")
    }

    @GetMapping("/API/chat/expert/{expertId}")
    @ResponseStatus(HttpStatus.OK)
    fun getChatByExpert(@PathVariable expertId: Long) : List<ChatDTO>? {
        return chatService.getChatByCustomer(expertId) ?: throw Exception("Expert not found")
    }

    @GetMapping("/API/chat/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    fun getChatByProduct(@PathVariable productId: Long) : List<ChatDTO>? {
        return chatService.getChatByCustomer(productId) ?: throw Exception("Product not found")
    }

    @GetMapping("/API/chat/date/{date}")
    @ResponseStatus(HttpStatus.OK)
    fun getChatByDate(@PathVariable date: String) : List<ChatDTO>? {
        val formattedDate = SimpleDateFormat("yyyy-MM-dd").parse(date)
        return chatService.getChatByDate(formattedDate) ?: throw Exception("Customer not found")
    }

    @PostMapping("/API/chat")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertChat(@RequestBody chatDTO: ChatDTO?) {
        if (chatDTO != null) {
            val insertChat = chatDTOToEntity(chatDTO)

            chatService.insertChat(insertChat)
        }
        else {
            throw Exception("Empty chat body")
        }
    }
}