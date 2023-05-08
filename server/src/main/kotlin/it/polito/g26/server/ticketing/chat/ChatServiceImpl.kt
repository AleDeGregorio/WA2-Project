package it.polito.g26.server.ticketing.chat

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

    override fun getChatByCustomer(customerId: Long): List<ChatDTO>? {
        return chatRepository.findByCustomer(customerId)?.map { it.toDTO() }
    }

    override fun getChatByExpert(expertId: Long): List<ChatDTO>? {
        return chatRepository.findByExpert(expertId)?.map { it.toDTO() }
    }

    override fun getChatByProduct(productId: Long): List<ChatDTO>? {
        return chatRepository.findByProduct(productId)?.map { it.toDTO() }
    }

    override fun getChatByDate(date: Date): List<ChatDTO>? {
        return chatRepository.findByDate(date)?.map { it.toDTO() }
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