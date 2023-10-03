package it.polito.g26.server.profiles.manager

import it.polito.g26.server.profiles.expert.Expert
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ManagerRepository : JpaRepository<Manager, String> {
    @Query("SELECT m FROM Manager m WHERE m.email = :email")
    fun getByEmail(@Param("email") email: String): Manager?
    //@Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Manager m WHERE m.id = :id")
    //fun existsById(@Param("id") id: String) : Boolean

    //@Query("SELECT m FROM Manager m WHERE m.id = :id")
    //fun findById(@Param("id") id: String) : Manager?

}