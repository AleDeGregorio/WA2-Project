package it.polito.g26.server.users
import it.polito.g26.server.ProfileAlreadyExistsException
import it.polito.g26.server.ProfileNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun findUserByEmail(email: String): UserDTO? {
        return userRepository.findByIdOrNull(email)?.toDTO()
    }

    override fun insertUserProfile(u: UserDTO){
        if (userRepository.findByIdOrNull(u.email) == null)
        {
            var user = User()
            user.email = u.email
            user.username = u.username
            user.age = u.age
            userRepository.save(user)
        }
        else
        {
            throw ProfileAlreadyExistsException("Email ${u.email} already exists!")
        }
    }

    override fun updateUserProfile(u: UserDTO): UserDTO?{
        var user = userRepository.findByIdOrNull(u.email) ?: throw ProfileNotFoundException("No profile with email ${u.email} found!")
        user.username = u.username
        user.age = u.age
        userRepository.save(user)
        return user.toDTO()
    }
}