package it.polito.g26.server.profiles.customer

import it.polito.g26.server.profiles.Profile
import it.polito.g26.server.ticketing.ticket.Ticket
import it.polito.g26.server.ticketing.utility.Role
import it.polito.g26.server.ticketing.utility.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
data class Customer(
    override var name: String,
    override var surname: String,

    //@Column(unique = true)
    override var email: String = "",

    var city: String = "",
    var address: String = "",

    @OneToMany(mappedBy = "customer")
    var tickets: MutableSet<Ticket> = mutableSetOf()
) : Profile(name = name, surname = surname, email = email, role = Role.CUSTOMER)
