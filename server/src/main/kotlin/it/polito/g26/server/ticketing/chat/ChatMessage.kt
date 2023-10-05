package it.polito.g26.server.ticketing.chat

data class ChatMessage @JvmOverloads constructor(
    var type: MessageType? = null,
    var content: String? = null,
    var sender: String? = null
)

enum class MessageType {
    CHAT,
    JOIN,
    LEAVE
}