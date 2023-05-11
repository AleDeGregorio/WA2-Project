package it.polito.g26.server.products

import it.polito.g26.server.ticketing.device.Device

interface ProductService {
    fun getAll(): List<ProductDTO>

    fun getProduct(ean: Long): ProductDTO?

    fun insertProduct(product: Product)

    fun updateProduct(product: Product)
}