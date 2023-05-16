package it.polito.g26.server.ticketing.chat

import it.polito.g26.server.ticketing.messages.Message
import it.polito.g26.server.ticketing.tickets.Ticket
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
    var ticket: Ticket? = null,

    @Temporal(TemporalType.TIMESTAMP)
    var creationDate: Date? = null
)