package it.polito.g26.server.ticketing.chat

import it.polito.g26.server.ticketing.message.MessageDTO
import it.polito.g26.server.ticketing.message.toDTO
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
            throw Exception("Chat not found")
        }
    }

    override fun insertChat(chat: Chat) {
        if (chatRepository.existsById(chat.id!!)) {
            throw Exception("Chat already exists")
        }
        else {
            chatRepository.save(chat)
        }
    }
}