package it.polito.g26.server.products

import it.polito.g26.server.*
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
        val productL = productService.getAll()
        if(productL.isNotEmpty())
            return productL
        else{
            throw ProductListIsEmptyException("Product List is Empty")
        }
    }

    @GetMapping("/API/products/{ean}")
    @ResponseStatus(HttpStatus.OK)
    fun getDevice(@PathVariable ean: Long) : ProductDTO? {
        return productService.getProduct(ean) ?: throw ProductNotFoundException("Product with ean $ean not found!")
    }

    @PostMapping("/API/products/")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertDevice(@RequestBody productDTO: ProductDTO?) {
        if (productDTO == null) {
            throw EmptyPostBodyException("Empty device body")
        } else if(productService.getProduct(productDTO.ean)!=null) {
            throw DuplicateProductException("${productDTO.ean} already in use!")
        }
        else{
            val insertProduct = productDTOToEntity(productDTO)
            productService.insertProduct(insertProduct)
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
            throw EmptyPostBodyException("Empty device body")
        }
    }
}