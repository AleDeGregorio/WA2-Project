package it.polito.g26.server.ticketing.experts

import it.polito.g26.server.ticketing.Expertise
import it.polito.g26.server.ticketing.tickets.Ticket
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ExpertRepository: JpaRepository<Expert, String> {
    @Query("SELECT e FROM Expert e WHERE e.expertises = :expertise")
    fun getExpertsByExpertise(@Param("expertise") expertise: Expertise): List<Expert>

    @Query("SELECT e FROM Expert e WHERE e.email = :email")
    fun findByEmail(@Param("email") email: String): Expert?

    @Query("Select t FROM Ticket t WHERE t.expert.profileId = :id")
    fun getAssignedTickets(@Param("id") id: String): List<Ticket>

    @Query("SELECT CASE WHEN (COUNT(e) > 0) THEN true ELSE false END FROM Expert e WHERE e.email = :email")
    fun existsByEmail(@Param("email") email: String): Boolean
}