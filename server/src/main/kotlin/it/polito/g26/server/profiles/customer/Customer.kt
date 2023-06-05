package it.polito.g26.server.profiles.customer

import it.polito.g26.server.profiles.Profile
import it.polito.g26.server.ticketing.tickets.Ticket
import it.polito.g26.server.ticketing.utility.Role
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
data class Customer(
    override var id: String?,
    override var username: String,
    override var firstName: String,
    override var lastName: String,

    @Column(unique = true)
    override var email: String = "",
    @Transient
    override var password: String = "",

    var city: String = "",
    var address: String = "",

    @OneToMany(mappedBy = "customer")
    var tickets: MutableSet<Ticket> = mutableSetOf()
) : Profile(id = id,  username = username, firstName = firstName, lastName = lastName, email = email, password = password)
