package it.polito.g26.server.products

import it.polito.g26.server.profiles.Profile
import it.polito.g26.server.profiles.ProfileDTO

data class ProductDTO(
    val ean: String,
    val name: String,
    val brand: String,
    val price: Double,
    val category: String,
)
fun Product.toDTO(): ProductDTO {
    return ProductDTO(ean, name, brand, price, category)
}
