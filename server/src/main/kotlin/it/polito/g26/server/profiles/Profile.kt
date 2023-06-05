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
    open var id: String?,
    open var username: String,
    open var firstName: String,
    open var lastName: String,
    @Transient
    open var password: String,
    open var email: String,

)