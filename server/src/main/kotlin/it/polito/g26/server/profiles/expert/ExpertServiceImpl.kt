package it.polito.g26.server.profiles.expert

import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.ticketing.tickets.toDTO
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ExpertServiceImpl(
    private val expertRepository: ExpertRepository
) : ExpertService {
    override fun getExpert(email: String): ExpertDTO? {
        return expertRepository.getByEmail(email)?.toDTO()
    }

    override fun getExpertsByField(field: String): List<ExpertDTO>? {
        return expertRepository.getByField(field)?.map { it.toDTO() }
    }

    override fun insertExpert(expert: Expert) {
        if (expert.id != null && expertRepository.existsById(expert.id!!)) {
            throw Exception("Expert already exists")
        }
        else {
            expertRepository.save(expert)
        }
    }

    override fun updateExpert(expert: Expert) {
        if (expertRepository.existsById(expert.id!!)) {
            val retrievedExpert = expertRepository.findById(expert.id!!).get()

            retrievedExpert.name = expert.name
            retrievedExpert.surname = expert.surname
            retrievedExpert.fields = expert.fields

            expertRepository.save(retrievedExpert)
        }
        else {
            throw Exception("Expert not found")
        }
    }

    override fun getTickets(id: String): Set<TicketDTO>? {
        if (expertRepository.existsById(id)) {
            val tickets = expertRepository.getTickets(id) ?: return null
            return tickets.map { it.toDTO() }.toSet()
        }
        else {
            throw Exception("Expert not found")
        }
    }
}