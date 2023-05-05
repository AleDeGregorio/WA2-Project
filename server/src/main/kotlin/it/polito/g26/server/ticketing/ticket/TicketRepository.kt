package it.polito.g26.server.ticketing.ticket

import it.polito.g26.server.ticketing.statusTicket.StatusTicket
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Date

@Repository
interface TicketRepository : JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t LEFT JOIN FETCH t.customer WHERE t.customer.id = :id")
    fun findByCustomer(@Param("id") id: Long) : List<Ticket>?

    @Query("SELECT t FROM Ticket t LEFT JOIN FETCH t.expert WHERE t.expert.id = :id")
    fun findByExpert(@Param("id") id: Long) : List<Ticket>?

    @Query("SELECT t FROM Ticket t LEFT JOIN FETCH t.product WHERE t.product.ean = :id")
    fun findByProduct(@Param("id") id: Long) : List<Ticket>?

    @Query("SELECT t FROM Ticket t WHERE DATE(t.dateOfCreation) = DATE(:dateOfCreation)")
    fun findByDateOfCreation(@Param("dateOfCreation") dateOfCreation: Date) : List<Ticket>?

    @Query("SELECT s FROM StatusTicket s WHERE s.ticketDate.id.id = :id")
    fun getStatusTicket(@Param("id") id: Long) : List<StatusTicket>?

    @Modifying
    @Query("UPDATE Ticket t SET t.priorityLevel = :priorityLevel WHERE t.id = :id")
    fun setPriorityLevel(id: Long, priorityLevel: Int)
}