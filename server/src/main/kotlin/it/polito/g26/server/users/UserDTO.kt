package it.polito.g26.server.users


data class UserDTO (
    val email:String,
    val username: String,
    val age:Int
)

fun User.toDTO(): UserDTO{
    return UserDTO(email, username, age)
}