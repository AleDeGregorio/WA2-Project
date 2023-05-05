package it.polito.g26.server.ticketing.expert

import it.polito.g26.server.ticketing.ticket.Ticket
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ExpertRepository : JpaRepository<Expert, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.expert.id = :id")
    fun getTickets(@Param("id") id: Long) : List<Ticket>?
}