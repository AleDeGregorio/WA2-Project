package it.polito.g26.server.profiles.expert

import it.polito.g26.server.*
import it.polito.g26.server.ticketing.tickets.TicketDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class ExpertController(
    private val expertService: ExpertService
) {
    private fun expertDTOToEntity(expertDTO: ExpertDTO) : Expert {
        val expert = Expert(name = expertDTO.name,
            surname = expertDTO.surname,
            email = expertDTO.email,
            fields = expertDTO.fields
        )
        if (expertDTO.id != null){
            expert.id = expertDTO.id
        }
        return expert
    }

    @GetMapping("/API/expert/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getExpert(@PathVariable email: String) : ExpertDTO? {
        return expertService.getExpert(email) ?: throw EmailNotFoundException("Expert with email $email not found!")
    }

    @GetMapping("/API/expert/field/{field}")
    @ResponseStatus(HttpStatus.OK)
    fun getExpertsByField(@PathVariable field: String) : List<ExpertDTO> {
        val experts = expertService.getExpertsByField(field)
        if (experts.isEmpty()) {
            throw ExpertNotFoundException("No expert found for field $field")
        }
        return experts
    }

    @PostMapping("/API/expert")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertExpert(@RequestBody expertDTO: ExpertDTO?) {
        if (expertDTO == null) {
            throw EmptyPostBodyException("Empty Expert body")
        }else if(expertService.getExpert(expertDTO.email)!=null){
            throw EmailAlreadyExistException("${expertDTO.email} already in use!")
        }else{
            val insertExpert = expertDTOToEntity(expertDTO)
            expertService.insertExpert(insertExpert)
        }
    }

    @PutMapping("/API/expert/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateExpert(@RequestBody expertDTO: ExpertDTO? ) {
        if (expertDTO != null) {
            val updateExpert = expertDTOToEntity(expertDTO)

            expertService.updateExpert(updateExpert)
        }
        else {
            throw EmptyPostBodyException("Empty Expert body")
        }
    }

    @GetMapping("/API/expert/tickets/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getExpertTickets(@PathVariable id: String) : Set<TicketDTO> {
        val tickets = expertService.getTickets(id) ?: throw UserNotFoundException("Expert with id $id not found!")
        if(tickets.isEmpty()) {
            throw TicketListIsEmptyException("Expert with id $id has no tickets")
        }
        else{
            return tickets;
        }
    }
}