package it.polito.g26.server.products

data class ProductDTO(
    val productId: String,
    val name: String,
    val brand: String,
    val category: String,
    val price: Double
)

fun Product.toDTO(): ProductDTO {
    return ProductDTO(productId, name, brand, category, price)
}
