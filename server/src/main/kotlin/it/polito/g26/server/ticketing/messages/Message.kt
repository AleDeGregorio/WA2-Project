package it.polito.g26.server.ticketing.messages

import it.polito.g26.server.ticketing.tickets.Ticket
import it.polito.g26.server.users.User
import jakarta.persistence.*

@Entity
@Table(name = "messages")
class Message{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    var messageId: Long? = null
    @ManyToOne()
    var ticket: Ticket? = null
    @ManyToOne()
    var sender: User? = null
    @ManyToOne()
    var receiver: User? = null
//gestire expert e customer
    var attachment: String = ""
}