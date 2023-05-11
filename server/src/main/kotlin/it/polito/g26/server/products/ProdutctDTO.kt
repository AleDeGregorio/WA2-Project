package it.polito.g26.server.products

import it.polito.g26.server.ticketing.tickets.Ticket

data class ProductDTO(
    val ean: Long,
    val name: String,
    val brand: String,
    val category: String,
    val price: Double,
    val tickets: MutableSet<Ticket>
)

fun Product.toDTO(): ProductDTO {
    return ProductDTO(ean!!, name, brand, category, price, tickets)
}
