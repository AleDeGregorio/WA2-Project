package it.polito.g26.server.profiles.expert

import it.polito.g26.server.*
import it.polito.g26.server.ticketing.tickets.TicketDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/expert")
class ExpertController(
    private val expertService: ExpertService
) {

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getExpert(@PathVariable email: String) : ExpertDTO? {
        return expertService.getExpert(email) ?: throw EmailNotFoundException("Expert with email $email not found!")
    }

    @GetMapping("/field/{field}")
    @ResponseStatus(HttpStatus.OK)
    fun getExpertsByField(@PathVariable field: String) : List<ExpertDTO> {
        val experts = expertService.getExpertsByField(field)
        if (experts.isEmpty()) {
            throw ExpertNotFoundException("No expert found for field $field")
        }
        return experts
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertExpert(@RequestBody expertDTO: ExpertDTO?) {
        println("hello $expertDTO")
        if (expertDTO == null) {
            throw EmptyPostBodyException("Empty Expert body")
        }else if(expertService.getExpert(expertDTO.email)!=null){
            throw EmailAlreadyExistException("${expertDTO.email} already in use!")
        }else{
            expertService.insertExpert(expertDTO.toEntity())
        }
    }

    @PutMapping("/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateExpert(@RequestBody expertDTO: ExpertDTO? ) {
        if (expertDTO != null) {
            expertService.updateExpert(expertDTO.toEntity())
        }
        else {
            throw EmptyPostBodyException("Empty Expert body")
        }
    }

    @GetMapping("/{id}/tickets")
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