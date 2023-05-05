package it.polito.g26.server.ticketing.customer

import it.polito.g26.server.ticketing.utility.Role
import it.polito.g26.server.ticketing.ticket.Ticket
import it.polito.g26.server.ticketing.utility.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
data class Customer(
    @Column(unique = true)
    var email: String = "",

    var city: String = "",
    var address: String = "",

    @OneToMany(mappedBy = "customer")
    var tickets: MutableSet<Ticket> = mutableSetOf()
) : User(role = Role.CUSTOMER)
