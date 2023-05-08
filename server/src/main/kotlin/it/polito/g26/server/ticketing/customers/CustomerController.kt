package it.polito.g26.server.ticketing.customers

import it.polito.g26.server.ticketing.tickets.TicketDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController(
    private val customerService: CustomerService
) {
    private fun toEntity(customerDTO: CustomerDTO): Customer {
        val customer: Customer = Customer()

        customer.email = customerDTO.email
        customer.name = customerDTO.name
        customer.surname = customerDTO.surname
        customer.nationality = customerDTO.nationality
        customer.city = customerDTO.city
        customer.address = customerDTO.address

        return customer
    }
    @GetMapping("/API/customer/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomerByEmail(@PathVariable email: String): CustomerDTO? {
        return customerService.getCustomerByEmail(email)
            ?: throw Exception("Customer not found")
    }

    @PostMapping("/API/customer")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody customerDTO: CustomerDTO?) {
        if (customerDTO != null) {
            val newCustomer = toEntity(customerDTO)
            customerService.createCustomer(newCustomer)
        }
        else {
            throw Exception("Empty customer body")
        }
    }

    @PutMapping("/API/customer/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateCustomer(@RequestBody customerDTO: CustomerDTO?) {
        if (customerDTO != null) {
            val updatedCustomer = toEntity(customerDTO)
            customerService.updateCustomer(updatedCustomer)
        }
        else {
            throw Exception("Empty customer body")
        }
    }

    @GetMapping("/API/customer/tickets/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomerTickets(@PathVariable id: String) : List<TicketDTO>? {
        return customerService.getTickets(id)
            ?: throw Exception("Customer not found")
    }
}