package it.polito.g26.server.ticketing.manager

import it.polito.g26.server.ticketing.utility.Role
import it.polito.g26.server.ticketing.utility.User
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
data class Manager(
    @Column(unique = true)
    var email: String = ""
) : User(role = Role.MANAGER)
