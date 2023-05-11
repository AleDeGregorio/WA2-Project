package it.polito.g26.server.profiles.expert

import it.polito.g26.server.ticketing.ticket.TicketDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class ExpertController(
    private val expertService: ExpertService
) {
    private fun expertDTOToEntity(expertDTO: ExpertDTO, id: Long?) : Expert {
        val expert = Expert(name = expertDTO.name!!, surname = expertDTO.surname!!, email = expertDTO.email!!)

        if (id != null) {
            expert.id = id
        }

        expert.fields = expertDTO.fields

        return expert
    }

    @GetMapping("/API/expert/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getExpert(@PathVariable id: Long) : ExpertDTO? {
        return expertService.getExpert(id) ?: throw Exception("Expert not found")
    }

    @GetMapping("/API/expert/field/{field}")
    @ResponseStatus(HttpStatus.OK)
    fun getExpertsByField(@PathVariable field: String) : List<ExpertDTO>? {
        return expertService.getExpertsByField(field) ?: throw Exception("No expert found")
    }

    @PostMapping("/API/expert")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertExpert(@RequestBody expertDTO: ExpertDTO?) {
        if (expertDTO != null) {
            val insertExpert = expertDTOToEntity(expertDTO, null)

            expertService.insertExpert(insertExpert)
        }
        else {
            throw Exception("Empty expert body")
        }
    }

    @PutMapping("/API/expert/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateExpert(@RequestBody expertDTO: ExpertDTO?, @PathVariable id: Long) {
        if (expertDTO != null) {
            val updateExpert = expertDTOToEntity(expertDTO, id)

            expertService.updateExpert(updateExpert)
        }
        else {
            throw Exception("Empty expert body")
        }
    }

    @GetMapping("/API/expert/tickets/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getExpertTickets(@PathVariable id: Long) : Set<TicketDTO> {
        return expertService.getTickets(id) ?: throw Exception("Expert not found")
    }
}