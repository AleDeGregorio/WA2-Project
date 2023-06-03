package it.polito.g26.server.profiles.manager

import it.polito.g26.server.EmailAlreadyExistException
import it.polito.g26.server.EmailNotFoundException
import it.polito.g26.server.EmptyPostBodyException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/manager")
class ManagerController(
    private val managerService: ManagerService
) {
    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getManager(@PathVariable email: String) : ManagerDTO? {
        return managerService.getManager(email) ?: throw EmailNotFoundException("Manager with email $email not found!")
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertManager(@RequestBody managerDTO: ManagerDTO?) {
        if (managerDTO == null) {
            throw EmptyPostBodyException("Empty Manager body")
        }else if(managerService.getManager(managerDTO.email)!=null){
            throw EmailAlreadyExistException("${managerDTO.email} already in use!")
        }else{
            managerService.insertManager(managerDTO.toEntity())
        }
    }

    @PutMapping("/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateManager(@RequestBody managerDTO: ManagerDTO?) {
        if (managerDTO != null) {
            managerService.updateManager(managerDTO.toEntity())
        }
        else {
            throw EmptyPostBodyException("Empty Manager body")
        }
    }
}