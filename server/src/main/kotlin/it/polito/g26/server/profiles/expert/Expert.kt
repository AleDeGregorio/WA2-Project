package it.polito.g26.server.profiles.expert

import it.polito.g26.server.profiles.Profile
import it.polito.g26.server.ticketing.tickets.Ticket
import it.polito.g26.server.ticketing.utility.Role
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
data class Expert (
    var fields: String = "",

    override var name: String,
    override var surname: String,
    override var email: String,

    @OneToMany(mappedBy = "expert")
    var tickets: MutableSet<Ticket> = mutableSetOf()
) : Profile(name = name, surname = surname, email = email, role = Role.EXPERT)