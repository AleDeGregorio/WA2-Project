package it.polito.g26.server.ticketing.manager

interface ManagerService {
    fun getManager(id: Long) : ManagerDTO?

    fun insertManager(manager: Manager)

    fun updateManager(manager: Manager)
}