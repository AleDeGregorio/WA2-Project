package it.polito.g26.server.ticketing.customers

import it.polito.g26.server.ticketing.tickets.Ticket
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: JpaRepository<Customer, String> {
    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    fun findByEmail(@Param("email") email: String): Customer?

    @Query("SELECT CASE WHEN (COUNT(c) > 0) THEN true ELSE false END FROM Customer c WHERE c.email = :email")
    fun existsByEmail(@Param("email") email: String): Boolean

    @Query("Select t FROM Ticket t WHERE t.customer.profileId = :id")
    fun getTickets(@Param("id") id: String): List<Ticket>
}