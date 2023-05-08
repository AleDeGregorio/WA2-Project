package it.polito.g26.server.users

import jakarta.persistence.*

@Entity
@Inheritance(strategy =  InheritanceType.TABLE_PER_CLASS)
open class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    var id: Long? = null
    var email: String = ""
    var name: String = ""
    var username: String = ""
    open val role: Role? = null
}