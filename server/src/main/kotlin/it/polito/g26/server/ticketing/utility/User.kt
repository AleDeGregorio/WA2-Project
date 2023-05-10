package it.polito.g26.server.ticketing.utility

import jakarta.persistence.*

@MappedSuperclass
open class User (
    @Id
    @GeneratedValue
    var id: Long? = null,

    open var name: String,
    open var surname: String,

    @Enumerated(value = EnumType.STRING)
    val role: Role
)