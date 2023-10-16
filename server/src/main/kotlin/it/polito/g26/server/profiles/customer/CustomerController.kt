package it.polito.g26.server.profiles.customer

import io.micrometer.observation.annotation.Observed
import it.polito.g26.server.*
import it.polito.g26.server.security.login.LoginController
import it.polito.g26.server.profiles.expert.ExpertDTO
import it.polito.g26.server.ticketing.tickets.TicketDTO
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customer")
@Observed
@Slf4j
class CustomerController(
    private val customerService: CustomerService
) {
    private val log = LoggerFactory.getLogger(CustomerController::class.java)

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomer(@PathVariable email: String) : CustomerDTO? {
        val customer = customerService.getCustomer(email)

        if (customer != null) {
            log.info("Getting customer $email")
            return customer
        }
        else {
            log.info("Getting customer $email failed: not found")
            throw EmailNotFoundException("Customer with email $email not found!")
        }
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun customerSignup(@RequestBody customerDTO: CustomerDTO?) {
        if (customerDTO == null) {
            log.info("Inserting customer failed: empty body")
            throw EmptyPostBodyException("Empty Costumer body")
        }
        else if (customerService.getCustomer(customerDTO.email) != null) {
            log.info("Inserting customer ${customerDTO.email} failed: email already in use")
            throw EmailAlreadyExistException("${customerDTO.email} already in use!")
        }
        else {
            log.info("Inserting customer ${customerDTO.email}")
            customerService.customerSignup(customerDTO.toEntity())
        }
    }

    @PutMapping("/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateCustomer(@RequestBody customerDTO: CustomerDTO?, @PathVariable email: String) {
        if (customerDTO != null) {
            log.info("Updating customer $email")
            customerService.updateCustomer(customerDTO.toEntity())
        }
        else {
            log.info("Updating customer $email failed: empty body")
            throw EmptyPostBodyException("Empty Costumer body")
        }
    }

    @GetMapping("/{id}/tickets")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomerTickets(@PathVariable id: String) : Set<TicketDTO>? {
        val tickets = customerService.getTickets(id)

        if (tickets == null) {
            log.info("Getting customer $id tickets failed: customer not found")
            throw UserNotFoundException("Customer with id $id not found!")
        }

        if(tickets.isEmpty()) {
            log.info("Getting customer $id tickets failed: no tickets present")
            throw TicketListIsEmptyException("Customer with id $id has no tickets")
        }
        else {
            log.info("Getting customer $id tickets")
            return tickets
        }
    }
}