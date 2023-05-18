package it.polito.g26.server.profiles.manager

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class ManagerController(
    private val managerService: ManagerService
) {
    private fun managerDTOToEntity(managerDTO: ManagerDTO) : Manager {
        val manager = Manager(name = managerDTO.name, surname = managerDTO.surname, email = managerDTO.email, department = managerDTO.department)
        manager.id = managerDTO.id

        return manager
    }

    @GetMapping("/API/manager/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getManager(@PathVariable email: String) : ManagerDTO? {
        return managerService.getManager(email) ?: throw Exception("Manager not found")
    }

    @PostMapping("/API/manager")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertManager(@RequestBody managerDTO: ManagerDTO?) {
        if (managerDTO != null) {
            val insertManager = managerDTOToEntity(managerDTO)

            managerService.insertManager(insertManager)
        }
        else {
            throw Exception("Empty manager body")
        }
    }

    @PutMapping("/API/manager/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateManager(@RequestBody managerDTO: ManagerDTO?) {
        if (managerDTO != null) {
            val updateManager = managerDTOToEntity(managerDTO)

            managerService.updateManager(updateManager)
        }
        else {
            throw Exception("Empty manager body")
        }
    }
}