package it.polito.g26.server.ticketing.statusTicket

import io.micrometer.observation.annotation.Observed
import it.polito.g26.server.EmptyPostBodyException
import it.polito.g26.server.TicketNotFoundException
import it.polito.g26.server.products.ProductDTO
import it.polito.g26.server.security.login.LoginController
import it.polito.g26.server.ticketing.tickets.toEntity
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ticket")
@Observed
@Slf4j
class StatusTicketController(
    @Autowired
    private val statusTicketService: StatusTicketService
) {
    private val log = LoggerFactory.getLogger(StatusTicketController::class.java)

    @GetMapping("/status/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getStatusTicket(@PathVariable id: Long) : List<StatusTicketDTO>? {
        val status = statusTicketService.getStatusTicket(id)

        if (status != null) {
            log.info("Getting status for ticket $id")
            return status
        }
        else {
            log.info("Getting status for ticket $id failed: ticket not found")
            throw TicketNotFoundException("Ticket with id $id not found!")
        }
    }

    @GetMapping("/status/{id}/latest")
    @ResponseStatus(HttpStatus.OK)
    fun getLatestStatus(@PathVariable id: Long) : StatusTicketDTO? {
        val status = statusTicketService.getLatestStatus(id)

        if (status != null) {
            log.info("Getting latest status for ticket $id")
            return status
        }
        else {
            log.info("Getting latest status for ticket $id failed: ticket not found")
            throw TicketNotFoundException("Ticket with id $id not found!")
        }
    }

    @PostMapping("/status/open")
    @ResponseStatus(HttpStatus.CREATED)
    fun openStatusTicket(@RequestBody statusTicketDTO: StatusTicketDTO?) {
        if (statusTicketDTO != null) {
            log.info("Opening ticket ${statusTicketDTO.tid?.id}")
            statusTicketService.openStatusTicket(statusTicketDTO.toEntity())
        }
        else {
            log.info("Opening ticket failed: empty body")
            throw EmptyPostBodyException("Empty Status Ticket body")
        }
    }
    @PostMapping("/status/close")
    @ResponseStatus(HttpStatus.CREATED)
    fun closeStatusTicket(@RequestBody statusTicketDTO: StatusTicketDTO?) {
        if (statusTicketDTO != null) {
            log.info("Closing ticket ${statusTicketDTO.tid?.id}")
            statusTicketService.closeStatusTicket(statusTicketDTO.toEntity())
        }
        else {
            log.info("Closing ticket failed: empty body")
            throw EmptyPostBodyException("Empty Status Ticket body")
        }
    }
    @PostMapping("/status/progress")
    @ResponseStatus(HttpStatus.CREATED)
    fun progressStatusTicket(@RequestBody statusTicketDTO: StatusTicketDTO?) {
        if (statusTicketDTO != null) {
            log.info("Progressing ticket ${statusTicketDTO.tid?.id}")
            statusTicketService.progressStatusTicket(statusTicketDTO.toEntity())
        }
        else {
            log.info("Progressing ticket failed: empty body")
            throw EmptyPostBodyException("Empty Status Ticket body")
        }
    }
    @PostMapping("/status/reopen")
    @ResponseStatus(HttpStatus.CREATED)
    fun reopenStatusTicket(@RequestBody statusTicketDTO: StatusTicketDTO?) {
        if (statusTicketDTO != null) {
            log.info("Reopening ticket ${statusTicketDTO.tid?.id}")
            statusTicketService.reopenStatusTicket(statusTicketDTO.toEntity())
        }
        else {
            log.info("Reopening ticket failed: empty body")
            throw EmptyPostBodyException("Empty Status Ticket body")
        }
    }
    @PostMapping("/status/resolve")
    @ResponseStatus(HttpStatus.CREATED)
    fun resolveStatusTicket(@RequestBody statusTicketDTO: StatusTicketDTO?) {
        if (statusTicketDTO != null) {
            log.info("Resolving ticket ${statusTicketDTO.tid?.id}")
            statusTicketService.resolveStatusTicket(statusTicketDTO.toEntity())
        }
        else {
            log.info("Resolving ticket failed: empty body")
            throw EmptyPostBodyException("Empty Status Ticket body")
        }
    }

}