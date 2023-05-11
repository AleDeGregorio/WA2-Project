package it.polito.g26.server.profiles.manager

data class ManagerDTO(
    val id: Long?,
    val name: String?,
    val surname: String?,
    val email: String?,
    val department: String?
)

fun Manager.toDTO() : ManagerDTO {
    return ManagerDTO(id, name, surname, email, department)
}