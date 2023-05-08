package it.polito.g26.server.profiles

data class ProfileDTO(
    val email: String,
    val password: String,
    val name: String,
    val surname: String,
    val city: String,
    val address: String
)

fun Profile.toDTO(): ProfileDTO {
    return ProfileDTO(email, password, name, surname, city, address)
}