package it.polito.g26.server.ticketing.statuses

import it.polito.g26.server.ticketing.experts.ExpertDTO
import it.polito.g26.server.ticketing.experts.toDTO
import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.ticketing.tickets.toDTO
import java.time.LocalDate

data class StatusDTO(
    val statusId: Long?,
    val date: LocalDate?,
    val status: it.polito.g26.server.ticketing.Status?,
    val ticket: TicketDTO?,
    val expertAssigned: ExpertDTO?
)

fun Status.toDTO(): StatusDTO{
    return StatusDTO(
        statusId,
        date,
        status,
        ticket?.toDTO(),
        expertAssigned?.toDTO())
}