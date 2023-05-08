package it.polito.g26.server.ticketing.expert

import it.polito.g26.server.ticketing.chat.Chat
import it.polito.g26.server.ticketing.utility.Role
import it.polito.g26.server.ticketing.ticket.Ticket
import it.polito.g26.server.ticketing.utility.User
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
data class Expert (
    var fields: String = "",

    @OneToMany(mappedBy = "expert")
    var tickets: MutableSet<Ticket> = mutableSetOf(),
    @OneToMany(mappedBy = "expert")
    var chats: MutableSet<Chat> = mutableSetOf()
) : User(role = Role.EXPERT)