package it.polito.g26.server.ticketing.messages

import it.polito.g26.server.ticketing.attachment.AttachmentDTO

interface MessageService {
    fun getMessage(id: Long) : MessageDTO?

    fun getMessageByChat(chatId: Long) : List<MessageDTO>?

    fun getAttachments(id: Long) : Set<AttachmentDTO>?

    fun insertMessage(message: Message) : Long?
}