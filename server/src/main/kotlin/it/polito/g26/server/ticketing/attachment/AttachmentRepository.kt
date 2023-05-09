package it.polito.g26.server.ticketing.attachment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AttachmentRepository : JpaRepository<Attachment, Long> {
    @Query("SELECT a FROM Attachment a LEFT JOIN FETCH a.message WHERE a.message.id = :id")
    fun findByMessage(@Param("id") id: Long) : List<Attachment>?
}