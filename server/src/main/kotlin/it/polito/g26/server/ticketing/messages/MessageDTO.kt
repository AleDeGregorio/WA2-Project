package it.polito.g26.server.ticketing.messages

import it.polito.g26.server.profiles.Profile
import it.polito.g26.server.profiles.ProfileDTO
import it.polito.g26.server.profiles.toDTO
import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.ticketing.tickets.toDTO
import java.time.LocalDate
import java.util.Date

data class MessageDTO(
    val messageId: Long?,
    val sender: ProfileDTO?,
    val receiver: ProfileDTO?,
    val ticket: TicketDTO?,
    val date: LocalDate?,
    val attachment: String?
)

fun Message.toDTO(): MessageDTO{
    return MessageDTO(
        messageId,
        sender?.toDTO(),
        receiver?.toDTO(),
        ticket?.toDTO(),
        date,
        attachment
    )
}

