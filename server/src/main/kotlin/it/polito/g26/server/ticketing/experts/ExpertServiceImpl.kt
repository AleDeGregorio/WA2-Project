package it.polito.g26.server.ticketing.experts

import it.polito.g26.server.ticketing.Expertise
import it.polito.g26.server.ticketing.tickets.Ticket
import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.ticketing.tickets.toDTO
import org.springframework.stereotype.Service
import kotlin.math.exp

@Service
class ExpertServiceImpl (
    private val expertRepository: ExpertRepository
): ExpertService {
    override fun getExpert(email: String): ExpertDTO? {
        return expertRepository.findByEmail(email)?.toDTO()
    }

    override fun getExperts(): List<ExpertDTO> {
        return expertRepository.findAll().map { it.toDTO() }
    }

    override fun createExpert(expert: Expert) {
        if (expertRepository.existsByEmail(expert.email)){
            throw Exception("Expert already present!")
        }
        else {
            expertRepository.save(expert)
        }
    }

    override fun updateExpert(expert: Expert) {
        if (expertRepository.existsByEmail(expert.email)){
            expertRepository.save(expert)
        }
        else {
            throw Exception("Expert not present in database yet!")
        }
    }

    override fun getAssignedTickets(id: String): List<TicketDTO> {
        return expertRepository.getAssignedTickets(id).map { it.toDTO() }
    }

    override fun getExpertsByExpertise(expertise: Expertise): List<ExpertDTO> {
        return expertRepository.getExpertsByExpertise(expertise).map { it.toDTO() }.toList()
    }
}