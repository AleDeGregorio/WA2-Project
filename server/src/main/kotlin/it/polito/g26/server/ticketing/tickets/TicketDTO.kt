package it.polito.g26.server.ticketing.tickets


import it.polito.g26.server.products.ProductDTO

import it.polito.g26.server.ticketing.customers.CustomerDTO

import it.polito.g26.server.ticketing.experts.ExpertDTO
import it.polito.g26.server.ticketing.messages.MessageDTO

import it.polito.g26.server.ticketing.status.StatusDTO

import java.util.*

data class TicketDTO (
    val id: Long?,
    val type: String,
    val description: String,
    val priority: Int = 0,
    val creationDate: Date,
    val customer: CustomerDTO? = null,
    val expert: ExpertDTO? = null,
    val status : MutableSet<StatusDTO>,
    val product: ProductDTO? = null,
    val messages: MutableSet<MessageDTO>
)
