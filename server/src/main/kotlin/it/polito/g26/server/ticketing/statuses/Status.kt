package it.polito.g26.server.ticketing.statuses

import it.polito.g26.server.ticketing.Status
import it.polito.g26.server.ticketing.experts.Expert
import it.polito.g26.server.ticketing.tickets.Ticket
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "statuses")
class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var statusId: Long? = null
    var date: LocalDate? = null
    @Enumerated(value = EnumType.ORDINAL)
    val status: it.polito.g26.server.ticketing.Status? = null
    @ManyToOne
    var ticket: Ticket? = null
    @OneToOne
    var expertAssigned: Expert? = null
}