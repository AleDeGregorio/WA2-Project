package it.polito.g26.server.ticketing.customer

import it.polito.g26.server.ticketing.chat.ChatDTO
import it.polito.g26.server.ticketing.chat.toDTO
import it.polito.g26.server.ticketing.ticket.TicketDTO
import it.polito.g26.server.ticketing.ticket.toDTO
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
            throw Exception("Email already exists")
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
            throw Exception("Customer not found")
        }
    }

    override fun getTickets(id: Long): Set<TicketDTO>? {
        if (customerRepository.existsById(id)) {
            val tickets = customerRepository.getTickets(id) ?: return null
            return tickets.map { it.toDTO() }.toSet()
        }
        else {
            throw Exception("Customer not found")
        }
    }

    override fun getChats(id: Long): Set<ChatDTO>? {
        if (customerRepository.existsById(id)) {
            val chats = customerRepository.getChats(id) ?: return null
            return chats.map { it.toDTO() }.toSet()
        }
        else {
            throw Exception("Customer not found")
        }
    }
}