package it.polito.g26.server.ticketing.statusTicket

interface StatusTicketService {
    fun getStatusTicket(id: Long) : List<StatusTicketDTO>?

    fun getLatestStatus(id: Long) : StatusTicketDTO?

    fun insertStatusTicket(statusTicket: StatusTicket)
}