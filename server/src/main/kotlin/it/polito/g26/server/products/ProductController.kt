package it.polito.g26.server.products

import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class ProductController(
    private val productService: ProductService
) {

    private fun productDTOToEntity(deviceDTO: ProductDTO) : Product {
        val device = Product(deviceDTO.ean)

        device.name = deviceDTO.name
        device.category = deviceDTO.category
        device.brand = deviceDTO.brand
        device.price = deviceDTO.price

        return device
    }

    @GetMapping("/API/products")
    @ResponseStatus(HttpStatus.OK)
    fun getAll() : List<ProductDTO> {
        return productService.getAll()
    }

    @GetMapping("/API/products/{ean}")
    @ResponseStatus(HttpStatus.OK)
    fun getDevice(@PathVariable ean: Long) : ProductDTO? {
        return productService.getProduct(ean) ?: throw Exception("Product not found")
    }

    @PostMapping("/API/products/")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertDevice(@RequestBody productDTO: ProductDTO?) {
        if (productDTO != null) {
            val insertProduct = productDTOToEntity(productDTO)

            productService.insertProduct(insertProduct)
        }
        else {
            throw Exception("Empty device body")
        }
    }

    @PutMapping("/API/products/{ean}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateDevice(@RequestBody productDTO: ProductDTO?, @PathVariable ean: Long) {
        if (productDTO != null) {
            val updatedProduct = productDTOToEntity(productDTO)

            productService.updateProduct(updatedProduct)
        }
        else {
            throw Exception("Empty device body")
        }
    }
}