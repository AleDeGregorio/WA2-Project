package it.polito.g26.server.ticketing.chat

import it.polito.g26.server.ticketing.customer.Customer
import it.polito.g26.server.ticketing.device.Device
import it.polito.g26.server.ticketing.expert.Expert
import it.polito.g26.server.ticketing.message.Message
import jakarta.persistence.*
import java.util.*

@Entity
data class Chat (
    @Id
    @GeneratedValue
    var id: Long? = null,

    @OneToMany(mappedBy = "chat")
    var messages: MutableSet<Message> = mutableSetOf(),

    @ManyToOne
    var customer: Customer? = null,
    @ManyToOne
    var expert: Expert? = null,
    @ManyToOne
    var product: Device? = null,

    @Temporal(TemporalType.TIMESTAMP)
    var creationDate: Date? = null
)