package it.polito.g26.server.products

import it.polito.g26.server.ticketing.tickets.TicketDTO

data class ProductDTO(
    val ean: Long,
    val name:String,
    val brand:String,
    val category: String,
    val price: Double
)

fun Product.toDTO(): ProductDTO{
    return ProductDTO(ean, name, brand, category, price)
}

fun ProductDTO.toEntity(): Product {
    return Product(ean, name, brand, category, price)
}