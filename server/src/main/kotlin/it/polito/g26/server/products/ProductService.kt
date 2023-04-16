package it.polito.g26.server.products

interface ProductService {
    fun getAll(): List<ProductDTO>
    fun getProductById(ean: String): ProductDTO?
}