package it.polito.g26.server.ticketing.experts

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var fieldId: Long? = null
    var fieldName: String = ""
}