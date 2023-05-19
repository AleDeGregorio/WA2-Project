package it.polito.g26.server.profiles

import it.polito.g26.server.ticketing.utility.Role
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Table

@MappedSuperclass
open class Profile (
    @Id
    @GeneratedValue
    var id: String? = null,

    open var name: String,
    open var surname: String,
    open var email: String,

    @Enumerated(value = EnumType.STRING)
    val role: Role
)