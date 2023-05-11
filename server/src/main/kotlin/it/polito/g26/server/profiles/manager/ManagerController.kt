package it.polito.g26.server.profiles.manager

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class ManagerController(
    private val managerService: ManagerService
) {
    private fun managerDTOToEntity(managerDTO: ManagerDTO, id: Long?) : Manager {
        val manager = Manager(name = managerDTO.name!!, surname = managerDTO.surname!!, email = managerDTO.email!!, department = managerDTO.department!!)

        if (id != null) {
            manager.id = id
        }

        return manager
    }

    @GetMapping("/API/manager/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getManager(@PathVariable id: Long) : ManagerDTO? {
        return managerService.getManager(id) ?: throw Exception("Manager not found")
    }

    @PostMapping("/API/manager")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertManager(@RequestBody managerDTO: ManagerDTO?) {
        if (managerDTO != null) {
            val insertManager = managerDTOToEntity(managerDTO, null)

            managerService.insertManager(insertManager)
        }
        else {
            throw Exception("Empty manager body")
        }
    }

    @PutMapping("/API/manager/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateManager(@RequestBody managerDTO: ManagerDTO?, @PathVariable id: Long) {
        if (managerDTO != null) {
            val updateManager = managerDTOToEntity(managerDTO, id)

            managerService.updateManager(updateManager)
        }
        else {
            throw Exception("Empty manager body")
        }
    }
}