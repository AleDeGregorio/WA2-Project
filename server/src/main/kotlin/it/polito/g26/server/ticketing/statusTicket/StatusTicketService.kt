package it.polito.g26.server.ticketing.statusTicket

interface StatusTicketService {
    fun getStatusTicket(id: Long) : List<StatusTicketDTO>?
    fun getLatestStatus(id: Long) : StatusTicketDTO?
    fun openStatusTicket(statusTicket: StatusTicket)
    fun closeStatusTicket(statusTicket: StatusTicket)
    fun reopenStatusTicket(statusTicket: StatusTicket)
    fun resolveStatusTicket(statusTicket: StatusTicket)
    fun progressStatusTicket(statusTicket: StatusTicket)
}