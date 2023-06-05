package it.polito.g26.server.profiles.manager

import it.polito.g26.server.profiles.Profile
import it.polito.g26.server.ticketing.utility.Role
import jakarta.persistence.Entity

@Entity
data class Manager(
    override var id: String?,
    override var username: String,
    override var firstName: String,
    override var lastName: String,
    @Transient
    override var password: String,
    override var email: String,

    var department: String
) : Profile(id = id, username = username, firstName = firstName, lastName = lastName, email = email, password = password)
