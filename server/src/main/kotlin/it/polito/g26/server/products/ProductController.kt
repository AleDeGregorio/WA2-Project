package it.polito.g26.server.products

import it.polito.g26.server.ProductNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    private val productService: ProductService
) {

    @GetMapping("/API/products/")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<ProductDTO> {
        return productService.getAll()
    }

    @GetMapping("/API/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    fun getProduct(@PathVariable productId: String): ProductDTO? {
        return productService.getProduct(productId) ?: throw ProductNotFoundException("Product not found")
    }
}