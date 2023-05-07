package it.polito.g26.server.ticketing.messages

import it.polito.g26.server.profiles.Profile
import it.polito.g26.server.ticketing.tickets.Ticket
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.Date

@Entity
@Table(name = "messages")
class Message (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var MessageId: Long? = null,
    @ManyToOne
    var sender: Profile? = null,
    @ManyToOne
    var receiver: Profile? = null,
    @ManyToOne
    var ticket: Ticket? = null,
    var date: Date? = null,
    var attachment: String? = null
)