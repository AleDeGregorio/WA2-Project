package it.polito.g26.server.profiles

import jakarta.persistence.*
import java.util.UUID

@Entity
@Inheritance(strategy =  InheritanceType.TABLE_PER_CLASS)
abstract class Profile (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var profileId: UUID? = null,
    @Column(nullable = false)
    var email: String= "",
    @Column(nullable = false)
    var password: String = "",
    var name: String = "",
    var surname: String = "",
    var nationality: String = ""
)