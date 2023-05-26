package it.polito.g26.server.ticketing.tickets

import it.polito.g26.server.products.Product
import it.polito.g26.server.ticketing.chat.Chat
import it.polito.g26.server.profiles.customer.Customer
import it.polito.g26.server.profiles.expert.Expert
import it.polito.g26.server.ticketing.statusTicket.StatusTicket
import jakarta.persistence.*
import java.util.*

@Entity
data class Ticket(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @ManyToOne
    var customer: Customer? = null,
    @ManyToOne
    var expert: Expert? = null,
    @ManyToOne
    var product: Product? = null,

    @OneToMany
    var status: MutableSet<StatusTicket>? = mutableSetOf(),
    @OneToMany(mappedBy = "ticket")
    var chats: MutableSet<Chat> = mutableSetOf(),

    var issueType: String = "",
    @Column(length = 10000)
    var description: String = "",
    var priorityLevel: Int? = null,
    @Temporal(TemporalType.TIMESTAMP)
    var dateOfCreation: Date? = null
)
