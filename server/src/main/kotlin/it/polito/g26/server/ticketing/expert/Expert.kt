package it.polito.g26.server.ticketing.expert

import it.polito.g26.server.ticketing.ticket.Ticket
import it.polito.g26.server.ticketing.utility.Role
import it.polito.g26.server.ticketing.utility.User
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
data class Expert (
    var fields: String = "",

    override var name: String,
    override var surname: String,

    @OneToMany(mappedBy = "expert")
    var tickets: MutableSet<Ticket> = mutableSetOf()
) : User(name = name, surname = surname,role = Role.EXPERT)