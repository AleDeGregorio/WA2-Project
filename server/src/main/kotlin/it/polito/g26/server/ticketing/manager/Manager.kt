package it.polito.g26.server.ticketing.manager

import it.polito.g26.server.ticketing.utility.Role
import it.polito.g26.server.ticketing.utility.User
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
data class Manager(
    override var name: String,
    override var surname: String
) : User(name = name, surname = surname, role = Role.MANAGER)
