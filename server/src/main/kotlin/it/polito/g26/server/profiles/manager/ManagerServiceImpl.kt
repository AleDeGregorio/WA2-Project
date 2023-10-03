package it.polito.g26.server.profiles.manager

import it.polito.g26.server.UserAlreadyExistException
import it.polito.g26.server.UserNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ManagerServiceImpl(
    private val managerRepository: ManagerRepository
) : ManagerService {
    override fun getManager(email: String): ManagerDTO? {
        return managerRepository.getByEmail(email)?.toDTO()
    }

    override fun insertManager(manager: Manager) {
        if (manager.id != null && managerRepository.existsById(manager.id!!)) {
            throw UserAlreadyExistException("Manager with id ${manager.id} already exist")
        }
        else {
            managerRepository.save(manager)
        }
    }

    override fun updateManager(manager: Manager) {
        if (managerRepository.existsById(manager.id!!)) {
            val retrievedManager = managerRepository.findById(manager.id!!).get()

            retrievedManager.username = manager.username
            retrievedManager.firstName = manager.firstName
            retrievedManager.lastName = manager.lastName
            retrievedManager.department = manager.department

            managerRepository.save(retrievedManager)
        }
        else {
            throw UserNotFoundException("Manager with id ${manager.id} not found")
        }
    }
}