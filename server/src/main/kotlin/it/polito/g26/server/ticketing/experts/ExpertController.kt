package it.polito.g26.server.ticketing.experts

import it.polito.g26.server.ticketing.tickets.TicketDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class ExpertController (
    private val expertService: ExpertService
) {
    private fun toEntity(expertDTO: ExpertDTO): Expert{
        val expert: Expert = Expert()

        expert.profileId = expertDTO.profileId
        expert.email = expertDTO.email
        expert.name = expertDTO.name
        expert.surname = expertDTO.surname
        expert.nationality = expertDTO.nationality
        expert.expertises = expertDTO.expertises.toMutableList()

        return expert
    }
    @GetMapping("/API/experts/")
    @ResponseStatus(HttpStatus.OK)
    fun getExperts(): List<ExpertDTO> {
        return expertService.getExperts()
    }

    @GetMapping("/API/experts/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getExpert(@PathVariable email: String): ExpertDTO? {
        return expertService.getExpert(email)
            ?: throw Exception("Expert not found!")
    }

    @PostMapping("/API/experts/")
    @ResponseStatus(HttpStatus.CREATED)
    fun createExpert(@RequestBody expertDTO: ExpertDTO?) {
        if (expertDTO != null){
            val newExpert = toEntity(expertDTO)
            expertService.createExpert(newExpert)
        }
        else {
            throw Exception("Empty body")
        }

    }

    @PutMapping("/API/expert/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateExpert(@RequestBody expertDTO: ExpertDTO?) {
        if (expertDTO != null) {
            val updatedExpert = toEntity(expertDTO)
            expertService.updateExpert(updatedExpert)
        }
        else {
            throw Exception("Empty body")
        }
    }

    @GetMapping("/API/expert/tickets/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getExpertTickets(@PathVariable id: String) : List<TicketDTO> {
        return expertService.getAssignedTickets(id) ?: throw Exception("Expert not found")
    }
}