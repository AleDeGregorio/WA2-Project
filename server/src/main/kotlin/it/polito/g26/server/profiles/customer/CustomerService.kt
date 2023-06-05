package it.polito.g26.server.profiles.customer

import it.polito.g26.server.ticketing.tickets.TicketDTO

interface CustomerService {
    fun getCustomer(email: String) : CustomerDTO?
    fun customerSignup(customer:Customer)

    fun updateCustomer(customer: Customer)

    fun getTickets(id: String) : Set<TicketDTO>?
}