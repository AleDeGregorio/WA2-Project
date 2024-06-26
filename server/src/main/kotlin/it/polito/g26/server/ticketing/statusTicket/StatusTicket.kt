package it.polito.g26.server.ticketing.statusTicket

import it.polito.g26.server.ticketing.utility.Status
import it.polito.g26.server.ticketing.tickets.Ticket
import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Embeddable
data class TicketDate(
    @MapsId("id")
    @ManyToOne
    var ticket: Ticket? = null,
    @Temporal(TemporalType.TIMESTAMP)
    var lastModifiedDate: Date? = null
) : Serializable

@Entity
data class StatusTicket(
    @EmbeddedId
    var ticketDate: TicketDate? = null,
    @Enumerated(value = EnumType.STRING)
    var status:Status?  = null
)
