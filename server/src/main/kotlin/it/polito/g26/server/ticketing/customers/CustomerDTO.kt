package it.polito.g26.server.ticketing.customers

import it.polito.g26.server.profiles.Profile
import it.polito.g26.server.profiles.ProfileDTO
import it.polito.g26.server.profiles.toDTO
import it.polito.g26.server.profiles.toEntity
import it.polito.g26.server.ticketing.tickets.TicketDTO
import it.polito.g26.server.ticketing.tickets.toDTO
import java.util.*


data class CustomerDTO(
    val profileId: UUID,
    val email: String,
    val name: String,
    val surname: String,
    val nationality: String,
    val city: String,
    val address: String,
    val tickets: List<TicketDTO>
)

/*
fun Customer.toProfile(): Profile {
    return Profile(
        profileId = profileId,
        email = email,
        password = password,
        name = name,
        surname = surname,
        nationality = nationality
    )
}
*/

fun Customer.toDTO(): CustomerDTO {
    return CustomerDTO (
        profileId!!,
        email,
        name,
        surname,
        nationality,
        city,
        address,
        tickets = this.tickets.map { it.toDTO() }
    )
}
