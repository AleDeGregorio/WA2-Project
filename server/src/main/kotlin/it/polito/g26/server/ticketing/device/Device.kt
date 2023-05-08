package it.polito.g26.server.ticketing.device

import it.polito.g26.server.ticketing.chat.Chat
import it.polito.g26.server.ticketing.ticket.Ticket
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class Device(
    @Id
    @GeneratedValue
    var ean: Long? = null,

    var name: String = "",
    var category: String = "",
    var brand: String = "",
    var price: Double = 0.0,

    @OneToMany(mappedBy = "product")
    var tickets: MutableSet<Ticket> = mutableSetOf(),
    @OneToMany(mappedBy = "product")
    var chats: MutableSet<Chat> = mutableSetOf()
)
