package it.polito.g26.server.ticketing.message

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m LEFT JOIN FETCH m.chat WHERE m.chat.id = :id")
    fun findByChat(@Param("id") id: Long) : List<Message>?
}