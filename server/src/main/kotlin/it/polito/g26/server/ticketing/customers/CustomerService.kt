package it.polito.g26.server.ticketing.customers

import it.polito.g26.server.ticketing.tickets.Ticket
import it.polito.g26.server.ticketing.tickets.TicketDTO

interface CustomerService {
    fun getCustomerByEmail(email: String): CustomerDTO?
    fun createCustomer(customer: Customer)
    fun updateCustomer(customer: Customer)
    fun getTickets(id: String): List<TicketDTO>
}