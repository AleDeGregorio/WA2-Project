package it.polito.g26.server.users

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User {
    @Id
    var email: String = ""
    var username: String = ""
    var age: Int = 0
}