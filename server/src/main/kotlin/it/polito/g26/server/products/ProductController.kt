package it.polito.g26.server.products

import io.micrometer.observation.annotation.Observed
import it.polito.g26.server.*
import it.polito.g26.server.security.login.LoginController
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/product")
@Observed
@Slf4j
class ProductController(
    private val productService: ProductService
) {
    private val log = LoggerFactory.getLogger(ProductController::class.java)

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun getAll() : List<ProductDTO> {
        val productL = productService.getAll()
        if(productL.isNotEmpty()) {
            log.info("Getting all products")
            return productL
        }
        else {
            log.info("Getting all products failed: no product in list")
            throw ProductListIsEmptyException("Product List is Empty")
        }
    }

    @GetMapping("/{ean}")
    @ResponseStatus(HttpStatus.OK)
    fun getDevice(@PathVariable ean: Long) : ProductDTO? {
        val product = productService.getProduct(ean)

        if (product != null) {
            log.info("Getting product $ean")
            return productService.getProduct(ean)
        }
        else {
            log.info("Getting product $ean failed: not found")
            throw ProductNotFoundException("Product with ean $ean not found!")
        }
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertDevice(@RequestBody productDTO: ProductDTO?) {
        if (productDTO == null) {
            log.info("Inserting new product failed: empty body")
            throw EmptyPostBodyException("Empty device body")
        }
        else if(productService.getProduct(productDTO.ean)!=null) {
            log.info("Inserting new product failed: ean already in use")
            throw DuplicateProductException("${productDTO.ean} already in use!")
        }
        else {
            log.info("Inserting new product product")
            productService.insertProduct(productDTO.toEntity())
        }
    }


    @PutMapping("/{ean}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateDevice(@RequestBody productDTO: ProductDTO?, @PathVariable ean: Long) {
        if (productDTO != null) {
            log.info("Updating product $ean")
            productService.updateProduct(productDTO.toEntity())
        }
        else {
            log.info("Updating product $ean failed: empty body")
            throw EmptyPostBodyException("Empty device body")
        }
    }
}