package it.polito.g26.server.profiles.expert

import io.micrometer.observation.annotation.Observed
import it.polito.g26.server.*
import it.polito.g26.server.security.login.LoginController
import it.polito.g26.server.ticketing.tickets.TicketDTO
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/expert")
@Observed
@Slf4j
class ExpertController(
    private val expertService: ExpertService
) {
    private val log = LoggerFactory.getLogger(ExpertController::class.java)

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getExpert(@PathVariable email: String) : ExpertDTO? {
        val expert = expertService.getExpert(email)

        if (expert != null) {
            log.info("Getting expert $email")
            return expert
        }
        else {
            log.info("Getting expert $email failed: not found")
            throw EmailNotFoundException("Expert with email $email not found!")
        }
    }

    @GetMapping("/field/{field}")
    @ResponseStatus(HttpStatus.OK)
    fun getExpertsByField(@PathVariable field: String) : List<ExpertDTO> {
        val experts = expertService.getExpertsByField(field)
        if (experts.isEmpty()) {
            log.info("Getting expert with field $field failed: not found")
            throw ExpertNotFoundException("No expert found for field $field")
        }
        log.info("Getting expert with field $field")
        return experts
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertExpert(@RequestBody expertDTO: ExpertDTO?) {
        if (expertDTO == null) {
            log.info("Inserting expert failed: empty body")
            throw EmptyPostBodyException("Empty Expert body")
        }
        else if(expertService.getExpert(expertDTO.email) != null) {
            log.info("Inserting expert ${expertDTO.email} failed: email already in use")
            throw EmailAlreadyExistException("${expertDTO.email} already in use!")
        }
        else {
            log.info("Inserting expert ${expertDTO.email}")
            expertService.insertExpert(expertDTO.toEntity())
        }
    }

    @PutMapping("/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateExpert(@RequestBody expertDTO: ExpertDTO? ) {
        if (expertDTO != null) {
            log.info("Updating expert ${expertDTO.email}")
            expertService.updateExpert(expertDTO.toEntity())
        }
        else {
            log.info("Updating expert failed: empty body")
            throw EmptyPostBodyException("Empty Expert body")
        }
    }

    @GetMapping("/{id}/tickets")
    @ResponseStatus(HttpStatus.OK)
    fun getExpertTickets(@PathVariable id: Long) : Set<TicketDTO> {
        val tickets = expertService.getTickets(id)

        if (tickets == null) {
            log.info("Getting expert $id tickets failed: expert not found")
            throw UserNotFoundException("Expert with id $id not found!")
        }

        if (tickets.isEmpty()) {
            log.info("Getting expert $id tickets failed: no tickets")
            throw TicketListIsEmptyException("Expert with id $id has no tickets")
        }
        else {
            log.info("Getting expert $id tickets")
            return tickets
        }
    }
}