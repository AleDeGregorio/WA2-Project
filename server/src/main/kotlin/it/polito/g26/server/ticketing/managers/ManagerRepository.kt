package it.polito.g26.server.ticketing.managers

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ManagerRepository: JpaRepository<Manager, String> {
}