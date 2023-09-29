package it.polito.g26.server.ticketing.messages

import it.polito.g26.server.profiles.Profile
import it.polito.g26.server.ticketing.attachment.Attachment
import it.polito.g26.server.ticketing.chat.Chat
import it.polito.g26.server.ticketing.utility.Role
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
data class Message(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @ManyToOne
    var chat: Chat? = null,

    @OneToMany(mappedBy = "message")
    var attachments: MutableSet<Attachment> = mutableSetOf(),

    @ManyToOne
    var sender: Profile? = null,

    @Column(length = 10000)
    var content: String = "",

    @Temporal(TemporalType.TIMESTAMP)
    var timestamp: LocalDateTime? = null
)