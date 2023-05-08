package it.polito.g26.server.profiles

interface ProfileService {
    fun getProfile(email: String): ProfileDTO?
    fun createProfile(profile: Profile)
    fun updateProfile(profile: Profile)
}