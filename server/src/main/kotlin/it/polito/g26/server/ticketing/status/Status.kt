package it.polito.g26.server.ticketing.status

import it.polito.g26.server.ticketing.tickets.Ticket
import jakarta.persistence.*
import org.springframework.data.annotation.LastModifiedDate
import java.io.Serializable
import java.util.*


@Entity
@Table(name = "status")
class Status: Serializable {
    @EmbeddedId
    var statusId: StatusId? = null
    var status: String = ""
}


@Embeddable
class StatusId : Serializable {
    @ManyToOne()
    var ticket: Ticket? = null
    @LastModifiedDate
    lateinit var lastDate: Date
}