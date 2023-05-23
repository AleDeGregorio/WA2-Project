package it.polito.g26.server.profiles.customer

import it.polito.g26.server.ticketing.tickets.TicketDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customer")
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
        return customerService.getCustomer(email) ?: throw Exception("Profile not found")
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertCustomer(@RequestBody customerDTO: CustomerDTO?) {
        if (customerDTO != null) {
            val insertCustomer = customerDTOToEntity(customerDTO, null)

            customerService.insertCustomer(insertCustomer)
        }
        else {
            throw Exception("Empty customer body")
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
            throw Exception("Empty customer body")
        }
    }

    @GetMapping("/tickets/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomerTickets(@PathVariable id: String) : Set<TicketDTO>? {
        return customerService.getTickets(id) ?: throw Exception("Customer not found")
    }
}