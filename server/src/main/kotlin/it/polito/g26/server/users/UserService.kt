package it.polito.g26.server.users

interface UserService {
    fun findUserByEmail(email:String): UserDTO?
    fun insertUserProfile(u: UserDTO)
    fun updateUserProfile(u: UserDTO): UserDTO?
}