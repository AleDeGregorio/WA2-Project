package it.polito.g26.server.ticketing.chat

import it.polito.g26.server.ticketing.messages.Message
import it.polito.g26.server.ticketing.messages.MessageDTO
import it.polito.g26.server.ticketing.messages.toDTO
import it.polito.g26.server.ticketing.messages.toEntity
import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.ticketing.tickets.toDTO
import it.polito.g26.server.ticketing.tickets.toEntity
import java.util.*

data class ChatDTO(
    val id: Long?,
    val messages: MutableSet<MessageDTO>,
    val ticket: TicketDTO,
    val creationDate: Date?
)
//serve mettere il ticket nel DTO? alla fine non lo usiamo
fun Chat.toDTO() : ChatDTO {
    val m :MutableSet<MessageDTO> = mutableSetOf()
    for(message in messages){
        m.add(message.toDTO())
    }
    return ChatDTO(id, m, ticket!!.toDTO(), creationDate)
}
fun ChatDTO.toEntity() : Chat {
    val m :MutableSet<Message> = mutableSetOf()
    for(message in messages){
        m.add(message.toEntity())
    }
    return Chat(id, m, ticket.toEntity(), creationDate)
}