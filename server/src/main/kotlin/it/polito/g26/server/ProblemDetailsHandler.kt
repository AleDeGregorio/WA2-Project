package it.polito.g26.server

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

class ProductListEmptyException(message:String) :RuntimeException(message)
class ProductNotFoundException(message: String) : RuntimeException(message)
class ProfileNotFoundException(message: String) : RuntimeException(message)
class ProfileAlreadyExistsException(message: String) : RuntimeException(message)
@RestControllerAdvice
class ProblemDetailsHandler: ResponseEntityExceptionHandler(){

    @ExceptionHandler(ProductListEmptyException :: class)
    fun handleProductListEmpty(e: ProductListEmptyException): ProblemDetail {
        val d = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
        d.title = "Product List Empty"
        d.detail = e.message
        return d
    }

    @ExceptionHandler(ProductNotFoundException :: class)
    fun handleProductNotFoundException(e: ProductNotFoundException): ProblemDetail {
        val d = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
        d.title = "Product Not Found"
        d.detail = e.message
        return d
    }

    @ExceptionHandler(ProfileNotFoundException :: class)
    fun handleProfileNotFoundException(e: ProfileNotFoundException): ProblemDetail {
        val d = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
        d.title = "User Profile Not Found"
        d.detail = e.message
        return d
    }

    @ExceptionHandler(ProfileAlreadyExistsException :: class)
    fun handleProfileAlreadyExistsException(e: ProfileAlreadyExistsException): ProblemDetail {
        val d = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        d.title = "User Profile Not Found"
        d.detail = e.message
        return d
    }

}