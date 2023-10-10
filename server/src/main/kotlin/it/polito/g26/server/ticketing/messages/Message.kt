package it.polito.g26.server.ticketing.messages

import it.polito.g26.server.ticketing.attachment.Attachment
import it.polito.g26.server.ticketing.chat.Chat
import it.polito.g26.server.ticketing.utility.Role
import jakarta.persistence.*
import java.util.*

@Entity
data class Message(
    @Id
    //@GeneratedValue
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    @SequenceGenerator(name = "message_seq", initialValue = 7)
    var id: Long? = null,

    @ManyToOne
    var chat: Chat? = null,

    @OneToMany(mappedBy = "message")
    var attachments: MutableSet<Attachment> = mutableSetOf(),

    //@Enumerated(value = EnumType.STRING)
    var sentBy: String = "",

    @Column(length = 10000)
    var content: String = "",
    @Temporal(TemporalType.TIMESTAMP)
    var sendingDate: Date? = null
)