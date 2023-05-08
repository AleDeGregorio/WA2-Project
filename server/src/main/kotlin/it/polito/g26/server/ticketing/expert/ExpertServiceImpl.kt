package it.polito.g26.server.ticketing.expert

import it.polito.g26.server.ticketing.chat.ChatDTO
import it.polito.g26.server.ticketing.chat.toDTO
import it.polito.g26.server.ticketing.ticket.TicketDTO
import it.polito.g26.server.ticketing.ticket.toDTO
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ExpertServiceImpl(
    private val expertRepository: ExpertRepository
) : ExpertService {
    override fun getExpert(id: Long): ExpertDTO? {
        return expertRepository.findByIdOrNull(id)?.toDTO()
    }

    override fun insertExpert(expert: Expert) {
        if (expertRepository.existsById(expert.id!!)) {
            throw Exception("Expert already exists")
        }
        else {
            expertRepository.save(expert)
        }
    }

    override fun updateExpert(expert: Expert) {
        if (expertRepository.existsById(expert.id!!)) {
            expertRepository.save(expert)
        }
        else {
            throw Exception("Expert not found")
        }
    }

    override fun getTickets(id: Long): Set<TicketDTO>? {
        if (expertRepository.existsById(id)) {
            val tickets = expertRepository.getTickets(id) ?: return null
            return tickets.map { it.toDTO() }.toSet()
        }
        else {
            throw Exception("Expert not found")
        }
    }

    override fun getChats(id: Long): Set<ChatDTO>? {
        if (expertRepository.existsById(id)) {
            val chats = expertRepository.getChats(id) ?: return null
            return chats.map { it.toDTO() }.toSet()
        }
        else {
            throw Exception("Expert not found")
        }
    }
}