package it.polito.g26.server.ticketing.chat

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRepository : JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c LEFT JOIN FETCH c.customer WHERE c.customer.id = :id")
    fun findByCustomer(@Param("id") id: Long) : List<Chat>?

    @Query("SELECT c FROM Chat c LEFT JOIN FETCH c.expert WHERE c.expert.id = :id")
    fun findByExpert(@Param("id") id: Long) : List<Chat>?

    @Query("SELECT c FROM Chat c LEFT JOIN FETCH c.expert WHERE c.expert.id = :id")
    fun findByProduct(@Param("id") id: Long) : List<Chat>?

    @Query("SELECT c FROM Chat c WHERE DATE(c.creationDate) = DATE(:date)")
    fun findByDate(@Param("date") date: Date) : List<Chat>?
}