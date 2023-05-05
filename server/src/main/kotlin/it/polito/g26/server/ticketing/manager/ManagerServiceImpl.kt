package it.polito.g26.server.ticketing.manager

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ManagerServiceImpl(
    private val managerRepository: ManagerRepository
) : ManagerService {
    override fun getManager(id: Long): ManagerDTO? {
        return managerRepository.findByIdOrNull(id)?.toDTO()
    }

    override fun getManagerByEmail(email: String): ManagerDTO? {
        return managerRepository.findByEmail(email)?.toDTO()
    }

    override fun insertManager(manager: Manager) {
        if (managerRepository.existsById(manager.id!!)) {
            throw Exception("Manager already exists")
        }
        else {
            managerRepository.save(manager)
        }
    }

    override fun updateManager(manager: Manager) {
        if (managerRepository.existsById(manager.id!!)) {
            managerRepository.save(manager)
        }
        else {
            throw Exception("Manager not found")
        }
    }
}