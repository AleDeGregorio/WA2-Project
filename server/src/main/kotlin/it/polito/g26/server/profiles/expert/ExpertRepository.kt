package it.polito.g26.server.profiles.expert

import it.polito.g26.server.ticketing.tickets.Ticket
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ExpertRepository : JpaRepository<Expert, Long> {
    @Query("SELECT e FROM Expert e WHERE LOWER(e.fields) LIKE LOWER(CONCAT('%', :field, '%'))")
    fun getByField(@Param("field") field: String) : List<Expert>?

    @Query("SELECT e FROM Expert e WHERE e.email = :email")
    fun getByEmail(@Param("email") email: String): Expert?

    @Query("SELECT t FROM Ticket t WHERE t.expert.id = :id")
    fun getTickets(@Param("id") id: Long) : List<Ticket>?
}