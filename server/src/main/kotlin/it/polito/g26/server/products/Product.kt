package it.polito.g26.server.products

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "products")
class Product {
    @Id
    var ean: String = ""
    var name: String = ""
    var brand: String = ""
}