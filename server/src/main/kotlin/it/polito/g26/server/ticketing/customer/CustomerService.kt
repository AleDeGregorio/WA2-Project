package it.polito.g26.server.ticketing.customer

import it.polito.g26.server.ticketing.chat.ChatDTO
import it.polito.g26.server.ticketing.ticket.TicketDTO

interface CustomerService {
    fun getCustomer(email: String) : CustomerDTO?

    fun insertCustomer(customer: Customer)

    fun updateCustomer(customer: Customer)

    fun getTickets(id: Long) : Set<TicketDTO>?

    fun getChats(id: Long) : Set<ChatDTO>?
}