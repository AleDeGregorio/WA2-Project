package it.polito.g26.server.products
import it.polito.g26.server.ProductListEmptyException
import it.polito.g26.server.ProductNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(private val productService: ProductService){

    @GetMapping("/API/products/")
    @ResponseStatus(HttpStatus.OK)
    fun getAllProducts(): List<ProductDTO>?{
        if(productService.getAll().isNotEmpty()) {
            return productService.getAll()
        }
        else throw ProductListEmptyException("There's not product stored in the database at the moment!")
    }

    @GetMapping("/API/products/{ean}")
    @ResponseStatus(HttpStatus.OK)
    fun getProductById(@PathVariable ean: String): ProductDTO?{
        return productService.getProductById(ean) ?: throw ProductNotFoundException("Product with id $ean not found!")
    }
}