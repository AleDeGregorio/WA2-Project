package it.polito.g26.server.profiles

import it.polito.g26.server.EmailAlreadyExistException
import it.polito.g26.server.ProfileNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProfileServiceImpl(
    private val profileRepository: ProfileRepository
): ProfileService {
    override fun getProfile(email: String): ProfileDTO? {
        return profileRepository.findByIdOrNull(email)?.toDTO()
    }

    override fun createProfile(profile: Profile) {
        if (profileRepository.existsById(profile.email)) {
            throw EmailAlreadyExistException("Email already exists")
        }
        else {
            profileRepository.save(profile)
        }
    }

    override fun updateProfile(profile: Profile) {
        if (profileRepository.existsById(profile.email)) {
            profileRepository.save(profile)
        }
        else {
            throw ProfileNotFoundException("Profile not found")
        }
    }
}