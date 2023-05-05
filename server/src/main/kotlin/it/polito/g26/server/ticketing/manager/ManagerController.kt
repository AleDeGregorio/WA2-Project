package it.polito.g26.server.ticketing.manager

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ManagerController(
    private val managerService: ManagerService
) {
    private fun managerDTOToEntity(managerDTO: ManagerDTO, email: String?) : Manager {
        val manager = Manager()

        manager.name = managerDTO.name
        manager.surname = managerDTO.surname
        manager.email = email ?: managerDTO.email

        return manager
    }

    @GetMapping("/API/manager/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getManager(@PathVariable id: Long) : ManagerDTO? {
        return managerService.getManager(id) ?: throw Exception("Manager not found")
    }

    @GetMapping("/API/manager/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getManagerByEmail(@PathVariable email: String) : ManagerDTO? {
        return managerService.getManagerByEmail(email) ?: throw Exception("Manager not found")
    }

    @PostMapping("/API/manager")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertManager(managerDTO: ManagerDTO?) {
        if (managerDTO != null) {
            val insertManager = managerDTOToEntity(managerDTO, null)

            managerService.insertManager(insertManager)
        }
        else {
            throw Exception("Empty manager body")
        }
    }

    @PutMapping("/API/manager/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateManager(managerDTO: ManagerDTO?, @PathVariable email: String) {
        if (managerDTO != null) {
            val updateManager = managerDTOToEntity(managerDTO, email)

            managerService.updateManager(updateManager)
        }
        else {
            throw Exception("Empty manager body")
        }
    }
}