package it.polito.g26.server

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

class ProductNotFoundException(message: String) : RuntimeException(message)
class DuplicateProductException(message: String) : RuntimeException(message)
class ProfileNotFoundException(message: String) : RuntimeException(message)
class EmailAlreadyExistException(message: String) : RuntimeException(message)
class EmptyPostProfileException(message: String) : RuntimeException(message)

@RestControllerAdvice
class ProblemDetailsHandler: ResponseEntityExceptionHandler() {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFound(e: ProductNotFoundException) = ProblemDetail
        .forStatusAndDetail(HttpStatus.NOT_FOUND, e.message!!)

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateProductException::class)
    fun handleDuplicateProduct(e: DuplicateProductException) = ProblemDetail
        .forStatusAndDetail(HttpStatus.CONFLICT, e.message!!)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProfileNotFoundException::class)
    fun handleProfileNotFound(e: ProfileNotFoundException) = ProblemDetail
        .forStatusAndDetail(HttpStatus.NOT_FOUND, e.message!!)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailAlreadyExistException::class)
    fun handleEmailNotFoundError(e: EmailAlreadyExistException) = ProblemDetail
        .forStatusAndDetail(HttpStatus.BAD_REQUEST, e.message!!)

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(EmptyPostProfileException::class)
    fun handleEmptyPostProfileError(e: EmptyPostProfileException) = ProblemDetail
        .forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, e.message!!)
}