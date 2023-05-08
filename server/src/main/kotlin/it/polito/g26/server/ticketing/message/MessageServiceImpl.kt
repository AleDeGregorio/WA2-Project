package it.polito.g26.server.ticketing.message

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl(
    private val messageRepository: MessageRepository
) : MessageService {
    override fun getMessage(id: Long): MessageDTO? {
        return messageRepository.findByIdOrNull(id)?.toDTO()
    }

    override fun getMessageByChat(chatId: Long): List<MessageDTO>? {
        return messageRepository.findByChat(chatId)?.map { it.toDTO() }
    }

    override fun insertMessage(message: Message) {
        if (messageRepository.existsById(message.id!!)) {
            throw Exception("Message already inserted")
        }
        else {
            messageRepository.save(message)
        }
    }
}