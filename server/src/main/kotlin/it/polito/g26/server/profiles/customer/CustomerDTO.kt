package it.polito.g26.server.profiles.customer

import it.polito.g26.server.ticketing.tickets.Ticket
import it.polito.g26.server.ticketing.utility.Role

data class CustomerDTO (
    val id: Long?,
    val name: String,
    val surname: String,
    val role: Role,
    val email: String,
    val city: String,
    val address: String
)

fun Customer.toDTO() : CustomerDTO {
    return CustomerDTO(id, name, surname, role, email, city, address)
}

fun CustomerDTO.toEntity(): Customer {
    val customer = Customer(
        name= name, surname, email, city, address)
    customer.id = this.id
    return customer
}