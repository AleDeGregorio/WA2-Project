package it.polito.g26.server.ticketing.statusTicket


import it.polito.g26.server.*
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
            throw TicketNotFoundException("Ticket with id $id not found!")
        }
    }

    override fun getLatestStatus(id: Long): StatusTicketDTO? {
        if (statusTicketRepository.existsByTicketId(id)) {
            return statusTicketRepository.getLatestStatus(id)?.toDTO()
        } else {
            throw TicketNotFoundException("Ticket with id $id not found!")
        }
    }

    override fun openStatusTicket(statusTicket: StatusTicket) {
    if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!)) {
        throw StatusTicketAlreadyInsertedException("Status Ticket with id ${statusTicket.ticketDate} already exists")
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
                throw StatusTicketAlreadyOpenedException("Status ticket with id ${statusTicket.ticketDate} already opened")
            }
        }
    }

    override fun closeStatusTicket(statusTicket: StatusTicket) {
        if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!)) {
            throw StatusTicketAlreadyInsertedException("Status Ticket with id ${statusTicket.ticketDate} already exists")
        }
        var ticket = statusTicket.ticketDate?.id!!
        if(getLatestStatus(ticket.id!!)?.status?.equals(Status.CLOSED)!!) {
            throw StatusTicketAlreadyClosedException("Status ticket with id ${statusTicket.ticketDate} already closed")
        }else if (getLatestStatus(ticket.id!!)?.status?.equals(Status.UNDEFINED)!!) {
            throw StatusTicketUndefinedException("Status ticket with id ${statusTicket.ticketDate} is Undefined")
        }else{
            statusTicket.status = Status.CLOSED
            statusTicketRepository.save(statusTicket)
        }
    }

    override fun reopenStatusTicket(statusTicket: StatusTicket) {
        if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!)) {
            throw StatusTicketAlreadyInsertedException("Status Ticket with id ${statusTicket.ticketDate} already exists")
        }
        var ticket = statusTicket.ticketDate?.id!!
        if(!getLatestStatus(ticket.id!!)?.status?.equals(Status.CLOSED)!! && !getLatestStatus(ticket.id!!)?.status?.equals(Status.RESOLVED)!! ) {
            throw StatusTicketAlreadyOpenedException("Status ticket with id ${statusTicket.ticketDate} already opened")
        }else if (getLatestStatus(ticket.id!!)?.status?.equals(Status.UNDEFINED)!!) {
            throw StatusTicketUndefinedException("Status ticket with id ${statusTicket.ticketDate} is Undefined")
        }else{
            statusTicket.status = Status.REOPENED
            statusTicketRepository.save(statusTicket)
        }
    }

    override fun resolveStatusTicket(statusTicket: StatusTicket) {
        if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!)) {
            throw StatusTicketAlreadyInsertedException("Status Ticket with id ${statusTicket.ticketDate} already exists")
        }
        var ticket = statusTicket.ticketDate?.id!!
        if(getLatestStatus(ticket.id!!)?.status?.equals(Status.CLOSED)!! ||  getLatestStatus(ticket.id!!)?.status?.equals(Status.RESOLVED)!! ) {
            throw StatusTicketAlreadyClosedException("Status ticket with id ${statusTicket.ticketDate} already closed")
        }else if (getLatestStatus(ticket.id!!)?.status?.equals(Status.UNDEFINED)!!) {
            throw StatusTicketUndefinedException("Status ticket with id ${statusTicket.ticketDate} is Undefined")
    }else{
            statusTicket.status = Status.RESOLVED
            statusTicketRepository.save(statusTicket)
        }
    }

    override fun progressStatusTicket(statusTicket: StatusTicket) {
        if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!)) {
            throw StatusTicketAlreadyInsertedException("Status Ticket with id ${statusTicket.ticketDate} already exists")
        }
        var ticket = statusTicket.ticketDate?.id!!
        if(getLatestStatus(ticket.id!!)?.status?.equals(Status.CLOSED)!! && getLatestStatus(ticket.id!!)?.status?.equals(Status.RESOLVED)!! ) {
            throw StatusTicketAlreadyClosedException("Status ticket with id ${statusTicket.ticketDate} already closed")
        }else if (getLatestStatus(ticket.id!!)?.status?.equals(Status.UNDEFINED)!!) {
            throw StatusTicketUndefinedException("Status ticket with id ${statusTicket.ticketDate} is Undefined")
        }else{
            statusTicket.status = Status.IN_PROGRESS
            statusTicketRepository.save(statusTicket)
        }
    }
}





