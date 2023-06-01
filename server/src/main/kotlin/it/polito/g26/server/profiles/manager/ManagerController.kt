package it.polito.g26.server.profiles.manager

import io.micrometer.observation.annotation.Observed
import it.polito.g26.server.EmailAlreadyExistException
import it.polito.g26.server.EmailNotFoundException
import it.polito.g26.server.EmptyPostBodyException
import it.polito.g26.server.security.login.LoginController
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/manager")
@Observed
@Slf4j
class ManagerController(
    private val managerService: ManagerService
) {
    private val log = LoggerFactory.getLogger(ManagerController::class.java)

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getManager(@PathVariable email: String) : ManagerDTO? {
        val manager = managerService.getManager(email)

        if (manager != null) {
            log.info("Getting manager $email")
            return manager
        }
        else {
            log.info("Getting manager $email failed: not found")
            throw EmailNotFoundException("Manager with email $email not found!")
        }
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertManager(@RequestBody managerDTO: ManagerDTO?) {
        if (managerDTO == null) {
            log.info("Inserting manager failed: empty body")
            throw EmptyPostBodyException("Empty Manager body")
        }
        else if (managerService.getManager(managerDTO.email) != null) {
            log.info("Inserting manager ${managerDTO.email} failed: email already in use")
            throw EmailAlreadyExistException("${managerDTO.email} already in use!")
        }
        else {
            log.info("Inserting manager ${managerDTO.email}")
            managerService.insertManager(managerDTO.toEntity())
        }
    }

    @PutMapping("/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateManager(@RequestBody managerDTO: ManagerDTO?) {
        if (managerDTO != null) {
            log.info("Updating manager ${managerDTO.email}")
            managerService.updateManager(managerDTO.toEntity())
        }
        else {
            log.info("Updating manager failed: empty body")
            throw EmptyPostBodyException("Empty Manager body")
        }
    }
}