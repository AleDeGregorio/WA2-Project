package it.polito.g26.server.products

import it.polito.g26.server.ticketing.tickets.Ticket
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Product (
    @Id
    var ean: Long? = null,

    var name: String = "",
    var brand: String = "",
    var category: String = "",

    var price: Double = 0.0,

    @OneToMany(mappedBy = "product")
    var tickets: MutableSet<Ticket> = mutableSetOf()
)