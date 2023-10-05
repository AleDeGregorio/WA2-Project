package it.polito.g26.server.ticketing.chat

import it.polito.g26.server.ChatAlreadyExistsException
import it.polito.g26.server.ChatNotFoundException
import it.polito.g26.server.ticketing.messages.MessageDTO
import it.polito.g26.server.ticketing.messages.toDTO
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatServiceImpl(
    private val chatRepository: ChatRepository
) : ChatService {
    override fun getChat(id: Long): ChatDTO? {
        return chatRepository.findByIdOrNull(id)?.toDTO()
    }

    override fun getChatByTicket(ticketId: Long): List<ChatDTO>? {
        println("questo ritorno prima del dto")
        println(chatRepository.findByTicket(ticketId))
        println("lol")
        return chatRepository.findByTicket(ticketId)?.map { it.toDTO() }
    }

    override fun getChatByDate(date: Date): List<ChatDTO>? {
        return chatRepository.findByDate(date)?.map { it.toDTO() }
    }

    override fun getMessages(id: Long): Set<MessageDTO>? {
        if (chatRepository.existsById(id)) {
            val messages = chatRepository.getMessages(id) ?: return null
            return messages.map { it.toDTO() }.toSet()
        }
        else {
            throw throw ChatNotFoundException("Chat with id $id not found!")
        }
    }

    override fun insertChat(chat: Chat) {
        if (chat.id != null && chatRepository.existsById(chat.id!!)) {
            throw ChatAlreadyExistsException("${chat.id} already exists!")
        }
        else {
            chatRepository.save(chat)
        }
    }
}