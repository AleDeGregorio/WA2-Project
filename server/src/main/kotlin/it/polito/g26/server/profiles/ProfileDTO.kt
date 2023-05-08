package it.polito.g26.server.profiles

import java.util.*

data class ProfileDTO(
    val profileId: UUID,
    val email: String,
    val name: String,
    val surname: String,
    val nationality: String
)

fun Profile.toDTO(): ProfileDTO {
    return ProfileDTO(profileId!!, email, name, surname, nationality)
}

fun ProfileDTO.toEntity(): Profile {
    return Profile(
        profileId,
        email,
        name,
        surname,
        nationality
        )
}