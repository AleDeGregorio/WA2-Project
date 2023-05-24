package it.polito.g26.server.profiles.customer

import it.polito.g26.server.*
import it.polito.g26.server.ticketing.tickets.TicketDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/API/customer")
class CustomerController(
    private val customerService: CustomerService
) {
    private fun customerDTOToEntity(customerDTO: CustomerDTO, email: String?) : Customer {
        val customer = Customer(name = customerDTO.name!!, surname = customerDTO.surname!!)

        customer.email = email ?: customerDTO.email
        customer.city = customerDTO.city
        customer.address = customerDTO.address

        return customer
    }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomer(@PathVariable email: String) : CustomerDTO? {
        return customerService.getCustomer(email) ?:  throw EmailNotFoundException("Customer with email $email not found!")
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertCustomer(@RequestBody customerDTO: CustomerDTO?) {
        if (customerDTO == null) {
            throw EmptyPostBodyException("Empty Costumer body")
        }else if(customerService.getCustomer(customerDTO.email)!=null){
            throw EmailAlreadyExistException("${customerDTO.email} already in use!")
        }else{
            val insertCustomer = customerDTOToEntity(customerDTO, null)
            customerService.insertCustomer(insertCustomer)
        }
    }

    @PutMapping("/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateCustomer(@RequestBody customerDTO: CustomerDTO?, @PathVariable email: String) {
        if (customerDTO != null) {
            val updateCustomer = customerDTOToEntity(customerDTO, email)
            customerService.updateCustomer(updateCustomer)
        }
        else {
            throw EmptyPostBodyException("Empty Costumer body")
        }
    }

    @GetMapping("/tickets/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomerTickets(@PathVariable id: Long) : Set<TicketDTO>? {
        val tickets = customerService.getTickets(id) ?: throw UserNotFoundException("Customer with id $id not found!")
        if(tickets.isEmpty()) {
            throw TicketListIsEmptyException("Customer with id $id has no tickets")
        }
        else{
            return tickets;
            }
    }
}