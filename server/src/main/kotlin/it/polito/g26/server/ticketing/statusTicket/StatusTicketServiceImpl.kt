package it.polito.g26.server.ticketing.statusTicket


import it.polito.g26.server.ticketing.utility.Status
import org.springframework.stereotype.Service

@Service
class StatusTicketServiceImpl(
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
        if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!) && !statusTicketRepository.getLatestStatus(
                statusTicket.ticketDate?.id?.id!!
            )!!.equals(Status.RESOLVED)
        ) {
            throw Exception("Status ticket already inserted")
        } else {
            val tickDate = TicketDate(statusTicket.ticketDate?.id, statusTicket.ticketDate?.lastModifiedDate)
            val statTick = StatusTicket(tickDate, Status.OPEN)
            statusTicketRepository.save(statTick)


        }
    }

    override fun closeStatusTicket(statusTicket: StatusTicket) {
        if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!)) {
            val tickDate = TicketDate(statusTicket.ticketDate?.id)
            val statTick = StatusTicket(tickDate, Status.CLOSED)
            statusTicketRepository.save(statTick)
        } else {
            throw Exception("Status ticket cannot be Closed")
        }
    }

    override fun reopenStatusTicket(statusTicket: StatusTicket) {
        if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!) && (
                    !statusTicketRepository.getLatestStatus(statusTicket.ticketDate?.id?.id!!)!!
                        .equals(Status.CLOSED) ||
                            !statusTicketRepository.getLatestStatus(statusTicket.ticketDate?.id?.id!!)!!
                                .equals(Status.RESOLVED))
        ) {
            throw Exception("Status ticket cannot be Reopened")
        } else {
            val tickDate = TicketDate(statusTicket.ticketDate?.id)
            val statTick = StatusTicket(tickDate, Status.REOPENED)
            statusTicketRepository.save(statTick)
        }
    }

    override fun resolveStatusTicket(statusTicket: StatusTicket) {
        if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!) &&
            statusTicketRepository.getLatestStatus(statusTicket.ticketDate?.id?.id!!)!!.equals(Status.CLOSED)
        ) {
            throw Exception("Status ticket cannot be Resolved")
        } else {
            val tickDate = TicketDate(statusTicket.ticketDate?.id)
            val statTick = StatusTicket(tickDate, Status.RESOLVED)
            statusTicketRepository.save(statTick)
        }
    }

    override fun progressStatusTicket(statusTicket: StatusTicket) {
        if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!) && (
                    !statusTicketRepository.getLatestStatus(statusTicket.ticketDate?.id?.id!!)!!.equals(Status.OPEN) ||
                            !statusTicketRepository.getLatestStatus(statusTicket.ticketDate?.id?.id!!)!!
                                .equals(Status.REOPENED))
        ) {
            throw Exception("Status ticket cannot be updated")
        } else {
            val tickDate = TicketDate(statusTicket.ticketDate?.id)
            val statTick = StatusTicket(tickDate, Status.IN_PROGRESS)
            statusTicketRepository.save(statTick)
        }
    }
}





