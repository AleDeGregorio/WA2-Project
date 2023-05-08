package it.polito.g26.server.ticketing.customers

import it.polito.g26.server.ticketing.tickets.Ticket
import it.polito.g26.server.users.Role
import it.polito.g26.server.users.User
import jakarta.persistence.*

@Entity
@Table(name = "customer")
 class Customer(
    @OneToMany(mappedBy = "customer")
    var tickets: MutableSet<Ticket> = mutableSetOf(),
    override var role: Role = Role.CUSTOMER
) : User()
