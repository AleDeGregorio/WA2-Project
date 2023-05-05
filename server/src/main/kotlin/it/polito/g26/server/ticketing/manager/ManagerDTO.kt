package it.polito.g26.server.ticketing.manager

data class ManagerDTO(
    val id: Long?,
    val name: String?,
    val surname: String?,
    val email: String
)

fun Manager.toDTO() : ManagerDTO {
    return ManagerDTO(id, name, surname, email)
}