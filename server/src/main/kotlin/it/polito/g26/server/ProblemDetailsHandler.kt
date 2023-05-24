package it.polito.g26.server

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

class ProductNotFoundException(message: String) : RuntimeException(message)
class ProductListIsEmptyException(message: String) : RuntimeException(message)
class DuplicateProductException(message: String) : RuntimeException(message)
class EmailAlreadyExistException(message: String) : RuntimeException(message)
class EmptyPostBodyException(message: String) : RuntimeException(message)
class UserNotFoundException(message: String) : RuntimeException(message)
class TicketListIsEmptyException(message: String) : RuntimeException(message)
class EmailNotFoundException(message: String) : RuntimeException(message)
class ExpertNotFoundException(message: String) : RuntimeException(message)
class TicketNotFoundException(message: String) : RuntimeException(message)
class TicketAlreadyExistException(message: String) : RuntimeException(message)
class UserAlreadyExistException(message: String) : RuntimeException(message)
class MissingUserException(message: String) : RuntimeException(message)
class MissingProductException(message: String) : RuntimeException(message)
class StatusTicketAlreadyInsertedException(message: String) : RuntimeException(message)
class StatusTicketAlreadyOpenedException(message: String) : RuntimeException(message)
class StatusTicketAlreadyClosedException(message: String) : RuntimeException(message)
class StatusTicketUndefinedException(message: String) : RuntimeException(message)
@RestControllerAdvice
class ProblemDetailsHandler: ResponseEntityExceptionHandler() {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFoundError(e: ProductNotFoundException): ProblemDetail{
        val d = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
        d.title = "Product Not Found"
        d.detail = e.message
        return d
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductListIsEmptyException::class)
    fun handleProductListEmptyError(e: ProductListIsEmptyException): ProblemDetail{
        val d = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
        d.title = "No Product Found!"
        d.detail = e.message
        return d
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateProductException::class)
    fun handleDuplicateProductError(e: DuplicateProductException): ProblemDetail{
        val d = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        d.title = "Product Id Already Exists"
        d.detail = e.message
        return d
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmailNotFoundException::class)
    fun handleEmailNotFoundError(e: EmailNotFoundException) : ProblemDetail{
        val d = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
        d.title = "Email Not Found"
        d.detail = e.message
        return d
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundError(e: UserNotFoundException) : ProblemDetail{
        val d = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
        d.title = "User Not Found"
        d.detail = e.message
        return d
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailAlreadyExistException::class)
    fun handleEmailAlreadyExistsError(e: EmailAlreadyExistException): ProblemDetail{
    val d = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
    d.title = " Email Already Exists"
    d.detail = e.message
    return d
}

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(EmptyPostBodyException::class)
    fun handleEmptyPostProfileError(e: EmptyPostBodyException): ProblemDetail{
    val d = ProblemDetail.forStatus(HttpStatus.NOT_ACCEPTABLE)
    d.title = " Empty Body"
    d.detail = e.message
    return d
}
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TicketListIsEmptyException::class)
    fun handleTicketListEmptyError(e: TicketListIsEmptyException): ProblemDetail{
        val d = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
        d.title = "No Ticket Found!"
        d.detail = e.message
        return d
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ExpertNotFoundException::class)
    fun handleExpertNotFoundError(e: ExpertNotFoundException) : ProblemDetail{
        val d = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
        d.title = "Expert Not Found"
        d.detail = e.message
        return d
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TicketNotFoundException::class)
    fun handleTicketNotFoundError(e: TicketNotFoundException) : ProblemDetail{
        val d = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
        d.title = "Ticket Not Found"
        d.detail = e.message
        return d
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TicketAlreadyExistException::class)
    fun handleTicketAlreadyExistsError(e: TicketAlreadyExistException): ProblemDetail{
        val d = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        d.title = " Ticket Already Exists"
        d.detail = e.message
        return d
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistException::class)
    fun handleUserAlreadyExistsError(e: UserAlreadyExistException): ProblemDetail{
        val d = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        d.title = " User Already Exists"
        d.detail = e.message
        return d
    }
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(MissingUserException::class)
    fun handleMissingUserError(e: MissingUserException): ProblemDetail{
        val d = ProblemDetail.forStatus(HttpStatus.NOT_ACCEPTABLE)
        d.title = "Missing User in Body"
        d.detail = e.message
        return d
    }
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(MissingProductException::class)
    fun handleMissingProductError(e: MissingProductException): ProblemDetail{
        val d = ProblemDetail.forStatus(HttpStatus.NOT_ACCEPTABLE)
        d.title = "Missing Product in Body"
        d.detail = e.message
        return d
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(StatusTicketAlreadyInsertedException::class)
    fun handleStatusTicketAlreadyExistsError(e: StatusTicketAlreadyInsertedException): ProblemDetail{
        val d = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        d.title = "Status Ticket Already Exists"
        d.detail = e.message
        return d
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(StatusTicketAlreadyOpenedException::class)
    fun handleStatusTicketAlreadyOpenError(e: StatusTicketAlreadyOpenedException): ProblemDetail{
        val d = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        d.title = "Status Ticket Already Opened"
        d.detail = e.message
        return d
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(StatusTicketAlreadyClosedException::class)
    fun handleStatusTicketAlreadyCloseError(e: StatusTicketAlreadyClosedException): ProblemDetail{
        val d = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        d.title = "Status Ticket Already Closed"
        d.detail = e.message
        return d
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(StatusTicketUndefinedException::class)
    fun handleStatusTicketUndefinedError(e: StatusTicketUndefinedException): ProblemDetail{
        val d = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        d.title = "Status Ticket Undefined"
        d.detail = e.message
        return d
    }
}