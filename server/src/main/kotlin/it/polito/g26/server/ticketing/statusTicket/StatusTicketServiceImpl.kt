package it.polito.g26.server.ticketing.statusTicket


import it.polito.g26.server.*
import it.polito.g26.server.ticketing.utility.Status
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

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
        } else {
            val ticket = statusTicket.ticketDate?.id!!
            if (statusTicketRepository.existsByTicket(ticket.id!!)) {
                if (statusTicketRepository.existsByTicketId(ticket.id!!)) {
                    val status = getLatestStatus(ticket.id!!)?.status
                    if (status?.equals(Status.IN_PROGRESS)!!
                        || status.equals(null)
                    ) {
                        statusTicket.status = Status.OPEN
                        statusTicket.ticketDate?.lastModifiedDate = Instant.now() as Date
                        statusTicketRepository.save(statusTicket)
                    } else {
                        throw StatusTicketAlreadyOpenedException("Status ticket with id ${statusTicket.ticketDate} already opened")
                    }
                    statusTicket.status = Status.OPEN
                    statusTicket.ticketDate?.lastModifiedDate = Instant.now() as Date
                    statusTicketRepository.save(statusTicket)
                }
                throw TicketNotFoundException("Ticket with id ${statusTicket.ticketDate?.id} not fount!")
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





