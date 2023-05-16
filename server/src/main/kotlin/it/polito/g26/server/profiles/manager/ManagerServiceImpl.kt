package it.polito.g26.server.profiles.manager

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
            throw Exception("Manager already exists")
        }
        else {
            managerRepository.save(manager)
        }
    }

    override fun updateManager(manager: Manager) {
        if (managerRepository.existsById(manager.id!!)) {
            val retrievedManager = managerRepository.findById(manager.id!!).get()

            retrievedManager.name = manager.name
            retrievedManager.surname = manager.surname
            retrievedManager.department = manager.department

            managerRepository.save(retrievedManager)
        }
        else {
            throw Exception("Manager not found")
        }
    }
}