package it.polito.g26.server.ticketing.customers

import it.polito.g26.server.profiles.Profile
import it.polito.g26.server.ticketing.tickets.Ticket
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
//@Table(name = "customer")
data class Customer(
    var city: String = "",
    var address: String = "",
    @OneToMany(mappedBy = "customer")
    var tickets: MutableSet<Ticket> = mutableSetOf<Ticket>()
): Profile()