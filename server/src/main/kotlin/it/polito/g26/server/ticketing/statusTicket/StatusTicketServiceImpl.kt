package it.polito.g26.server.ticketing.statusTicket


import it.polito.g26.server.ticketing.utility.Status
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StatusTicketServiceImpl(
    @Autowired
    private val statusTicketRepository: StatusTicketRepository
) : StatusTicketService {


    override fun getStatusTicket(id: Long): List<StatusTicketDTO>? {
        if (statusTicketRepository.existsByTicketId(id)) {
            return statusTicketRepository.findByTicketId(id)?.map { it.toDTO() }
        } else {
            throw Exception("Ticket not found")
        }
    }

    override fun getLatestStatus(id: Long): StatusTicketDTO? {
        if (statusTicketRepository.existsByTicketId(id)) {
            return statusTicketRepository.getLatestStatus(id)?.toDTO()
        } else {
            throw Exception("Ticket not found")
        }
    }

/*
    override fun insertStatusTicket(statusTicket: StatusTicket) {
        if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!)) {
            throw Exception("Status ticket already inserted")
        }
        else {
            statusTicketRepository.save(statusTicket)
        }
    }

*/
    override fun openStatusTicket(statusTicket: StatusTicket) {
    if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!)) {
        throw Exception("Status ticket already inserted")
    }
    else {
        var ticket = statusTicket.ticketDate?.id!!
            if(getLatestStatus(ticket.id!!)?.status?.equals(Status.IN_PROGRESS)!!) {
                statusTicket.status = Status.OPEN
                statusTicketRepository.save(statusTicket)
            }else if (getLatestStatus(ticket.id!!)?.status?.equals(Status.UNDEFINED)!!){
                statusTicket.status = Status.OPEN
                statusTicketRepository.save(statusTicket)
            } else {
                throw Exception("Status ticket already open")
            }
        }
    }

    override fun closeStatusTicket(statusTicket: StatusTicket) {
        if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!)) {
            throw Exception("Status ticket already inserted")
        }
        var ticket = statusTicket.ticketDate?.id!!
        if(getLatestStatus(ticket.id!!)?.equals(Status.CLOSED)!!) {
            throw Exception("Status ticket already closed")
        }else{
            statusTicket.status = Status.CLOSED
            statusTicketRepository.save(statusTicket)
        }
    }

    override fun reopenStatusTicket(statusTicket: StatusTicket) {
        if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!)) {
            throw Exception("Status ticket already inserted")
        }
        var ticket = statusTicket.ticketDate?.id!!
        if(!getLatestStatus(ticket.id!!)?.equals(Status.CLOSED)!! || !getLatestStatus(ticket.id!!)?.equals(Status.RESOLVED)!! ) {
            throw Exception("Status ticket already open")
        }else{
            statusTicket.status = Status.REOPENED
            statusTicketRepository.save(statusTicket)
        }
    }

    override fun resolveStatusTicket(statusTicket: StatusTicket) {
        if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!)) {
            throw Exception("Status ticket already inserted")
        }
        var ticket = statusTicket.ticketDate?.id!!
        if(getLatestStatus(ticket.id!!)?.equals(Status.CLOSED)!! || getLatestStatus(ticket.id!!)?.equals(Status.RESOLVED)!! ) {
            throw Exception("Status ticket already closed")
        }else{
            statusTicket.status = Status.RESOLVED
            statusTicketRepository.save(statusTicket)
        }
    }

    override fun progressStatusTicket(statusTicket: StatusTicket) {
        if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!)) {
            throw Exception("Status ticket already inserted")
        }
        var ticket = statusTicket.ticketDate?.id!!
        if(getLatestStatus(ticket.id!!)?.equals(Status.CLOSED)!! || getLatestStatus(ticket.id!!)?.equals(Status.RESOLVED)!! ) {
            throw Exception("Status ticket already closed")
        }else{
            statusTicket.status = Status.IN_PROGRESS
            statusTicketRepository.save(statusTicket)
        }
    }
}





