package it.polito.g26.server.users
import it.polito.g26.server.EmailAlreadyExistException
import it.polito.g26.server.ProfileNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class UserController(private val userService: UserService) {

    @GetMapping("/API/profiles/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getUserProfile(@PathVariable email: String): UserDTO? {
        return userService.findUserByEmail(email)
            ?: throw ProfileNotFoundException("Profile with email $email does not exist!")
    }

    @PostMapping("/API/profiles")
    @ResponseStatus(HttpStatus.CREATED)
    fun postUserProfile(@RequestBody u: UserDTO) {
        if (userService.findUserByEmail(u.email) != null) {
            userService.insertUserProfile(u)
        } else throw EmailAlreadyExistException("Email  ${u.email}  already exists in the database!")
    }

    @PutMapping("/API/profiles/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun putUserProfile(@PathVariable email: String, @RequestBody u: UserDTO): UserDTO? {
            return userService.updateUserProfile(u)
    }
}