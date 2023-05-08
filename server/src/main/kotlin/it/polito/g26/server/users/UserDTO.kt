package it.polito.g26.server.users


data class UserDTO (
    val id: Long?,
    val email:String,
    val name: String,
    val username: String,
    val role: Role
)

fun User.toDTO(): UserDTO{
    return UserDTO(id, email, username, name, role)
}