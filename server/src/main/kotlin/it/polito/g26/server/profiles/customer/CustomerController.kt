package it.polito.g26.server.profiles.customer

import it.polito.g26.server.*
import it.polito.g26.server.ticketing.tickets.TicketDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customer")
class CustomerController(
    private val customerService: CustomerService
) {

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomer(@PathVariable email: String) : CustomerDTO? {
        return customerService.getCustomer(email) ?:  throw EmailNotFoundException("Customer with email $email not found!")
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun customerSignup(@RequestBody customerDTO: CustomerDTO?) {
        if (customerDTO == null) {
            throw EmptyPostBodyException("Empty Costumer body")
        }else if(customerService.getCustomer(customerDTO.email)!=null){
            throw EmailAlreadyExistException("${customerDTO.email} already in use!")
        }else{
            customerService.customerSignup(customerDTO.toEntity())
        }
    }

    @PutMapping("/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateCustomer(@RequestBody customerDTO: CustomerDTO?, @PathVariable email: String) {
        if (customerDTO != null) {
            customerService.updateCustomer(customerDTO.toEntity())
        }
        else {
            throw EmptyPostBodyException("Empty Costumer body")
        }
    }

    @GetMapping("/{id}/tickets")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomerTickets(@PathVariable id: String) : Set<TicketDTO>? {
        val tickets = customerService.getTickets(id) ?: throw UserNotFoundException("Customer with id $id not found!")
        if(tickets.isEmpty()) {
            throw TicketListIsEmptyException("Customer with id $id has no tickets")
        }
        else{
            return tickets;
            }
    }
}