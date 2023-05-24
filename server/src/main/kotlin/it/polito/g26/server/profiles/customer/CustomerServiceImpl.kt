package it.polito.g26.server.profiles.customer

import it.polito.g26.server.EmailAlreadyExistException
import it.polito.g26.server.EmailNotFoundException
import it.polito.g26.server.UserAlreadyExistException
import it.polito.g26.server.UserNotFoundException
import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.ticketing.tickets.toDTO
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository
) : CustomerService {
    override fun getCustomer(email: String): CustomerDTO? {
        return customerRepository.findByEmail(email)?.toDTO()
    }

    override fun insertCustomer(customer: Customer) {
        if (customerRepository.existsByEmail(customer.email)) {
            throw UserAlreadyExistException("Customer with id ${customer.id} already exist")
        }
        else {
            customerRepository.save(customer)
        }
    }

    override fun updateCustomer(customer: Customer) {
        if (customerRepository.existsByEmail(customer.email)) {
            val retrievedCustomer = customerRepository.findByEmail(customer.email)

            retrievedCustomer?.name = customer.name
            retrievedCustomer?.surname = customer.surname
            retrievedCustomer?.city = customer.city
            retrievedCustomer?.address = customer.address

            customerRepository.save(retrievedCustomer!!)
        }
        else {
            throw UserNotFoundException("Customer with id ${customer.id} not found!")
        }
    }

    override fun getTickets(id: String): Set<TicketDTO>? {
        if (customerRepository.existsById(id)) {
            val tickets = customerRepository.getTickets(id) ?: return null
            return tickets.map { it.toDTO() }.toSet()
        }
        else {
            throw EmailNotFoundException("Customer not found!")
        }
    }
}