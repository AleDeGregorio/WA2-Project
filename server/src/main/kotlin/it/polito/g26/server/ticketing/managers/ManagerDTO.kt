package it.polito.g26.server.ticketing.managers

import it.polito.g26.server.profiles.Profile
import it.polito.g26.server.profiles.ProfileDTO
import it.polito.g26.server.profiles.toDTO
import java.util.*

data class ManagerDTO(
    val profileId: UUID,
    val email: String,
    val name: String,
    val surname: String,
    val nationality: String,
    val department: String
)

/*
fun Manager.toProfile(): Profile {
    return Profile(
        profileId,
        email,
        password,
        name,
        surname,
        nationality
    )
}
*/
fun Manager.toDTO(): ManagerDTO{
    return ManagerDTO(
        profileId!!,
        email,
        name,
        surname,
        nationality,
        department
    )
}