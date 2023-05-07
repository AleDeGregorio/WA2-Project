package it.polito.g26.server.ticketing.customers

import it.polito.g26.server.profiles.Profile
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "customer")
class Customer(
    var address: String = ""
): Profile()