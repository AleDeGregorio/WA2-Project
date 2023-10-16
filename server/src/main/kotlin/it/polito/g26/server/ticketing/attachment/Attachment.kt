package it.polito.g26.server.ticketing.attachment

import it.polito.g26.server.ticketing.messages.Message
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne

@Entity
data class Attachment(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @ManyToOne
    var message: Message? = null,

    var name: String = "",
    var type: String = "",
    var imageData: ByteArray
)