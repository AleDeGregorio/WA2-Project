package it.polito.g26.server.profiles

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "profiles")
class Profile {
    @Id
    var email: String = ""
    var password: String = ""
    var name: String = ""
    var surname: String = ""
    var city: String = ""
    var address: String = ""
}