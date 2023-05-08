package it.polito.g26.server.ticketing.chat

import it.polito.g26.server.ticketing.customer.Customer
import it.polito.g26.server.ticketing.device.Device
import it.polito.g26.server.ticketing.expert.Expert
import it.polito.g26.server.ticketing.message.Message
import java.util.Date

data class ChatDTO(
    val id: Long?,
    val messages: MutableSet<Message>,
    val customer: Customer?,
    val expert: Expert?,
    val product: Device?,
    val creationDate: Date?
)

fun Chat.toDTO() : ChatDTO {
    return ChatDTO(id, messages, customer, expert, product,creationDate)
}