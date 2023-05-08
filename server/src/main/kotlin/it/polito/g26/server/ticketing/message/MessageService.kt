package it.polito.g26.server.ticketing.message

interface MessageService {
    fun getMessage(id: Long) : MessageDTO?

    fun getMessageByChat(chatId: Long) : List<MessageDTO>?

    fun insertMessage(message: Message)
}