package it.polito.g26.server.ticketing.tickets

import it.polito.g26.server.ticketing.statuses.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface TicketRepository: JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t LEFT JOIN FETCH t.customer WHERE t.customer.profileId = :id")
    fun findByCustomer(@Param("id") id: String) : List<Ticket>?

    @Query("SELECT t FROM Ticket t LEFT JOIN FETCH t.expert WHERE t.expert.profileId = :id")
    fun findByExpert(@Param("id") id: String) : List<Ticket>?

    @Query("SELECT t FROM Ticket t LEFT JOIN FETCH t.product WHERE t.product.productId = :id")
    fun findByProduct(@Param("id") id: Long) : List<Ticket>?

    @Query("SELECT t FROM Ticket t JOIN t.status s WHERE DATE(s.date) = DATE(:dateOfCreation)")
    fun findByDateOfCreation(@Param("dateOfCreation") dateOfCreation: LocalDate) : List<Ticket>?

    @Query("SELECT s FROM Status s WHERE s.ticket.id = :id")
    fun getStatusTicket(@Param("id") id: Long) : List<Status>?

    @Modifying
    @Query("UPDATE Ticket t SET t.priorityLevel = :priorityLevel WHERE t.id = :id")
    fun setPriorityLevel(id: Long, priorityLevel: Int)
}