package it.polito.g26.server.ticketing.statuses

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StatusRepository: JpaRepository<Status, String> {
}