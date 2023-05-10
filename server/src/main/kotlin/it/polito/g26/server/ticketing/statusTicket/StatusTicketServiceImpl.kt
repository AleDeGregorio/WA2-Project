package it.polito.g26.server.ticketing.statusTicket

import org.springframework.stereotype.Service

@Service
class StatusTicketServiceImpl(
    private val statusTicketRepository: StatusTicketRepository
) : StatusTicketService {
    override fun getStatusTicket(id: Long): List<StatusTicketDTO>? {
        if (statusTicketRepository.existsByTicketId(id)) {
            return statusTicketRepository.findByTicketId(id)?.map { it.toDTO() }
        }
        else {
            throw Exception("Ticket not found")
        }
    }

    override fun getLatestStatus(id: Long): StatusTicketDTO? {
        if (statusTicketRepository.existsByTicketId(id)) {
            return statusTicketRepository.getLatestStatus(id)?.toDTO()
        }
        else {
            throw Exception("Ticket not found")
        }
    }

    override fun insertStatusTicket(statusTicket: StatusTicket) {
        if (statusTicket.ticketDate != null && statusTicketRepository.existsById(statusTicket.ticketDate!!)) {
            throw Exception("Status ticket already inserted")
        }
        else {
            statusTicketRepository.save(statusTicket)
        }
    }
}