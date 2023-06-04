package it.polito.g26.server.profiles.customer

import it.polito.g26.server.ticketing.utility.Role

data class CustomerDTO (
    val id: String?,
    val name: String,
    val surname: String,
    val password: String,
    val role: Role,
    val email: String,
    val city: String,
    val address: String
)

fun Customer.toDTO() : CustomerDTO {
    return CustomerDTO(id, name, surname, password, role, email, city, address)
}

fun CustomerDTO.toEntity(): Customer {
    return Customer(
        id = id,
        name = name, surname = surname,
        password = password, email = email,
        city = city, address = address
    )
}