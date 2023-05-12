package it.polito.g26.server.profiles.manager

import it.polito.g26.server.profiles.expert.Expert
import it.polito.g26.server.profiles.expert.ExpertDTO

data class ManagerDTO(
    val id: Long?,
    val name: String,
    val surname: String,
    val email: String,
    val department: String
)

fun Manager.toDTO() : ManagerDTO {
    return ManagerDTO(id, name, surname, email, department)
}

fun ManagerDTO.toEntity(): Manager {
    val manager = Manager(name, surname, email, department)
    manager.id = id
    return manager
}