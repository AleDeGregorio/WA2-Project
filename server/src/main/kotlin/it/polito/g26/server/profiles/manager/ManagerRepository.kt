package it.polito.g26.server.profiles.manager

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ManagerRepository : JpaRepository<Manager, Long> {
    @Query("SELECT m FROM Manager m WHERE m.email = :email")
    fun getByEmail(@Param("email") email: String): Manager?
}