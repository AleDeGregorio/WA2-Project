package it.polito.g26.server.profiles

import it.polito.g26.server.ticketing.utility.Role
import jakarta.persistence.*
import kotlin.jvm.Transient

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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