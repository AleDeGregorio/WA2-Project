package it.polito.g26.server.profiles.manager

import it.polito.g26.server.profiles.Profile
import it.polito.g26.server.ticketing.utility.Role
import jakarta.persistence.Entity

@Entity
data class Manager(
    override var name: String,
    override var surname: String,
    override var email: String,

    var department: String
) : Profile(name = name, surname = surname, email = email, role = Role.MANAGER)
