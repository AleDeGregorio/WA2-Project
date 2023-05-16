package it.polito.g26.server.ticketing.chat

import it.polito.g26.server.ticketing.messages.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRepository : JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c LEFT JOIN FETCH c.ticket WHERE c.ticket.id = :id")
    fun findByTicket(@Param("id") id: Long) : List<Chat>?

    @Query("SELECT c FROM Chat c WHERE DATE(c.creationDate) = DATE(:date)")
    fun findByDate(@Param("date") date: Date) : List<Chat>?

    @Query("SELECT m FROM Message m WHERE m.chat.id = :id")
    fun getMessages(@Param("id") id: Long) : List<Message>?
}