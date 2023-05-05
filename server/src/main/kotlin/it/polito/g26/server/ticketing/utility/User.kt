package it.polito.g26.server.ticketing.utility

import jakarta.persistence.*

@MappedSuperclass
open class User (
    @Id
    @GeneratedValue
    var id: Long? = null,

    var name: String? = null,
    var surname: String? = null,

    @Enumerated(value = EnumType.STRING)
    val role: Role
)