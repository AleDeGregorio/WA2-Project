package it.polito.g26.server.ticketing.chat

import java.util.Date

interface ChatService {
    fun getChat(id: Long) : ChatDTO?

    fun getChatByCustomer(customerId: Long) : List<ChatDTO>?

    fun getChatByExpert(expertId: Long) : List<ChatDTO>?

    fun getChatByProduct(productId: Long) : List<ChatDTO>?

    fun getChatByDate(date: Date) : List<ChatDTO>?

    fun insertChat(chat: Chat)
}