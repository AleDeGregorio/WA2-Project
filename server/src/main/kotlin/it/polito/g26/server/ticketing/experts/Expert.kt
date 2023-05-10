package it.polito.g26.server.ticketing.experts

import it.polito.g26.server.profiles.Profile
import it.polito.g26.server.ticketing.tickets.Ticket
import jakarta.persistence.*

@Entity
//@Table(name = "experts")
class Expert(
    @ElementCollection
    var expertises: MutableList<Int> = mutableListOf(),
    @OneToMany(mappedBy = "expert")
    var tickets: MutableSet<Ticket> = mutableSetOf()
) : Profile()