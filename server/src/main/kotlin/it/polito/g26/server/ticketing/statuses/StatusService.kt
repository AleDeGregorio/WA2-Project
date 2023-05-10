package it.polito.g26.server.ticketing.statuses

interface StatusService {

    fun getLastStatus(ticketId: Long)
    fun getStatuses(ticketId: Long)


}