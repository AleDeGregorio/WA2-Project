package it.polito.g26.server.ticketing.managers
import it.polito.g26.server.profiles.Profile
import jakarta.persistence.*

@Entity
@Table(name = "managers")
class Manager(
    var department: String = ""
) : Profile()