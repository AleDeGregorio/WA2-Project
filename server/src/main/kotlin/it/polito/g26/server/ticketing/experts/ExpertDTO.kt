package it.polito.g26.server.ticketing.experts

import it.polito.g26.server.profiles.Profile
import it.polito.g26.server.profiles.ProfileDTO
import it.polito.g26.server.profiles.toDTO
import java.util.*
import kotlin.math.exp

data class ExpertDTO(
    val profileId: UUID,
    val email: String,
    val name: String,
    val surname: String,
    val nationality: String,
    val expertises: List<Int>
)

/*
fun Expert.toProfile(): Profile {
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
fun Expert.toDTO(): ExpertDTO{
    return ExpertDTO(
        profileId!!,
        email,
        name,
        surname,
        nationality,
        expertises
    )
}