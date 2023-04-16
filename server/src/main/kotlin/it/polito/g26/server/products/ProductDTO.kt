package it.polito.g26.server.products

data class ProductDTO (
    val ean: String,
    val name:String,
    val brand:String
    )

fun Product.toDTO(): ProductDTO{
    return ProductDTO(ean, name, brand)
}