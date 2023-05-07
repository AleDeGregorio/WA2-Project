package it.polito.g26.server.ticketing.tickets

import it.polito.g26.server.ticketing.messages.Message
import it.polito.g26.server.products.Product
import it.polito.g26.server.profiles.Profile
import it.polito.g26.server.ticketing.customers.Customer
import it.polito.g26.server.ticketing.experts.Expert
import it.polito.g26.server.ticketing.statuses.Status
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tickets")
class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false, nullable = false)
    var id: Long? = null
    var typeOfIssue: String = ""
    var description: String = ""
    var priorityLevel: Int? = null
    @ManyToOne
    var product: Product? = null
    @OneToOne(mappedBy = "expert")
    var expertAssigned: Expert? = null
    @OneToMany
    var status: MutableSet<Status> = mutableSetOf<Status>()
    @OneToOne(mappedBy = "customer")
    var customer: Customer? = null
    @OneToMany(mappedBy = "ticket")
    var chat = mutableSetOf<Message>()
}