package it.polito.g26.server.ticketing.statuses

import it.polito.g26.server.ticketing.experts.Expert
import it.polito.g26.server.ticketing.tickets.Ticket
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.Date

@Entity
@Table(name = "statuses")
class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var statusId: Long? = null
    var date: Date? = null
    var status: String? = null
    @ManyToOne
    var ticket: Ticket? = null
    @OneToOne
    var expertAssigned: Expert? = null
}