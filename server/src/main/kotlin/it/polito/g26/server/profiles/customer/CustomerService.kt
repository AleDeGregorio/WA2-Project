package it.polito.g26.server.profiles.customer

import it.polito.g26.server.ticketing.tickets.TicketDTO

interface CustomerService {
    fun getCustomer(email: String) : CustomerDTO?

    fun insertCustomer(customer: Customer)

    fun updateCustomer(customer: Customer)

    fun getTickets(id: Long) : Set<TicketDTO>?
}