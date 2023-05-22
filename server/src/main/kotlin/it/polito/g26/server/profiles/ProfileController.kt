package it.polito.g26.server.profiles

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/*

@RestController
class ProfileController(
    private val profileService: ProfileService
) {
    @GetMapping("/API/profiles/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getProfile(@PathVariable email: String): ProfileDTO? {
        return profileService.getProfile(email) ?: throw ProfileNotFoundException("Profile not found")
    }

    @PostMapping("/API/profiles")
    @ResponseStatus(HttpStatus.CREATED)
    fun createProfile(@RequestBody profileDTO: ProfileDTO?) {
        if (profileDTO != null) {
            val createProfile = profileDTOToEntity(profileDTO, null)

            profileService.createProfile(createProfile)
        }
        else {
            throw EmptyPostProfileException("Empty profile body")
        }
    }

    @PutMapping("/API/profiles/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateProfile(@RequestBody profileDTO: ProfileDTO?, @PathVariable email: String) {
        if (profileDTO != null) {
            val updateProfile = profileDTOToEntity(profileDTO, email)

            profileService.updateProfile(updateProfile)
        }
        else {
            throw EmptyPostProfileException("Empty profile body")
        }
    }

    private fun profileDTOToEntity(profileDTO: ProfileDTO, email: String?): Profile {
        val profile = Profile()

        profile.email = email ?: profileDTO.email
        profile.name = profileDTO.name
        profile.surname = profileDTO.surname

        return profile
    }
}

*/