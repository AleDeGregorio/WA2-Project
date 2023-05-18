package it.polito.g26.server.profiles.manager

interface ManagerService {
    fun getManager(email: String) : ManagerDTO?

    fun insertManager(manager: Manager)

    fun updateManager(manager: Manager)
}