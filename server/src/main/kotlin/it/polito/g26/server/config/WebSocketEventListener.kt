package it.polito.g26.server.config

import it.polito.g26.server.ticketing.chat.ChatMessage
import it.polito.g26.server.ticketing.chat.MessageType
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent

@Component
@RequiredArgsConstructor
@Slf4j
class WebSocketEventListener (
    private val messageTemplate: SimpMessageSendingOperations,

): ApplicationListener<SessionConnectEvent> {
    @EventListener
    public fun handleWebSocketDisconnectListener(
        event: SessionDisconnectEvent
    ){
        val headerAccessor: StompHeaderAccessor = StompHeaderAccessor.wrap(event.message)
        val username: String = headerAccessor.sessionAttributes?.get("username")?.toString() ?: ""
        val chatId: String = headerAccessor.sessionAttributes?.get("chat")?.toString() ?: ""
        if (username != null) {
            LoggerFactory.getLogger(this.javaClass).info("User disconnected: ${username}")
            val chatMessage = ChatMessage(MessageType.LEAVE, sender = username)
            messageTemplate.convertAndSend("/topic/${chatId}", chatMessage)
        }
    }

    override fun onApplicationEvent(event: SessionConnectEvent) {
        val accessor = StompHeaderAccessor.wrap(event.message)
        val sessionId = accessor.sessionId
        val username = accessor.user?.name

        // Handle WebSocket connection opening
        println("WebSocket connection opened - Session ID: $sessionId, User: $username")
    }
}
