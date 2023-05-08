package it.polito.g26.server.ticketing.experts

import it.polito.g26.server.ticketing.managers.Manager
import it.polito.g26.server.ticketing.tickets.Ticket
import it.polito.g26.server.users.Role
import it.polito.g26.server.users.User
import jakarta.persistence.Entity
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "expert")
class Expert(
    @OneToMany(mappedBy = "expert")
    var tickets: MutableSet<Ticket> = mutableSetOf(),
    override var role: Role = Role.EXPERT,
    var field: String = "",
    @ManyToMany(mappedBy = "expert")
    var managers: MutableSet<Manager> = mutableSetOf()
) : User()