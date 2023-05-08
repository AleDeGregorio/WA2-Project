package it.polito.g26.server.ticketing.tickets

import it.polito.g26.server.ticketing.messages.Message
import it.polito.g26.server.products.Product
import it.polito.g26.server.ticketing.customers.Customer
import it.polito.g26.server.ticketing.status.Status
import it.polito.g26.server.ticketing.experts.Expert
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.util.*

@Entity
@Table(name = "tickets")
class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
        generator = "ticket_generator")
    @SequenceGenerator(name = "ticket_generator",
        sequenceName = "sequence_1",
        initialValue = 1,
        allocationSize = 1)
    @Column(updatable = false, nullable = false)
    var id: Long? = null
    var type: String = ""
    var description: String = ""
    var priority: Int = 0
    @CreatedDate
    lateinit var creationDate: Date
    @ManyToOne()
    var customer: Customer? = null
    @ManyToOne()
    var expert: Expert? = null
    @OneToMany(mappedBy = "tickets")
    var status = mutableSetOf<Status>()
    @ManyToOne()
    var product: Product? = null
    @OneToMany(mappedBy = "tickets")
    var messages = mutableSetOf<Message>()
}