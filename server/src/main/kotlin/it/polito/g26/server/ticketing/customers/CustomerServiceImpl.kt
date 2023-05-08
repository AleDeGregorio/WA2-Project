package it.polito.g26.server.ticketing.customers

import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.ticketing.tickets.toDTO
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository
): CustomerService {
    override fun getCustomerByEmail(email: String): CustomerDTO? {
        return customerRepository.findByEmail(email)?.toDTO()
    }

    override fun createCustomer(customer: Customer) {
        if (customerRepository.existsByEmail(customer.email)) {
            throw Exception("Email address already used!")
        }
        else {
            customerRepository.save(customer)
        }
    }

    override fun updateCustomer(customer: Customer) {
        if (customerRepository.existsByEmail(customer.email)) {
            customerRepository.save(customer)
        }
        else {
            throw Exception("Customer not found!")
        }
    }

    override fun getTickets(id: String): List<TicketDTO> {
        return customerRepository.getTickets(id).map { it.toDTO() }
    }

}