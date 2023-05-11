package it.polito.g26.server.products

import it.polito.g26.server.ProductNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

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

    @GetMapping("/API/devices")
    @ResponseStatus(HttpStatus.OK)
    fun getAll() : List<ProductDTO> {
        return productService.getAll()
    }

    @GetMapping("/API/device/{ean}")
    @ResponseStatus(HttpStatus.OK)
    fun getDevice(@PathVariable ean: Long) : ProductDTO? {
        return productService.getProduct(ean) ?: throw Exception("Product not found")
    }

    @PostMapping("/API/device")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertDevice(@RequestBody deviceDTO: ProductDTO?) {
        if (deviceDTO != null) {
            val insertProduct = productDTOToEntity(deviceDTO)

            productService.insertProduct(insertProduct)
        }
        else {
            throw Exception("Empty device body")
        }
    }

    @PutMapping("/API/device/{ean}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateDevice(@RequestBody deviceDTO: ProductDTO?, @PathVariable ean: Long) {
        if (deviceDTO != null) {
            val updatedProduct = productDTOToEntity(deviceDTO)

            productService.updateProduct(updatedProduct)
        }
        else {
            throw Exception("Empty device body")
        }
    }
}