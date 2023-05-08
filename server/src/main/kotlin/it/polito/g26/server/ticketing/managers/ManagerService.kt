package it.polito.g26.server.ticketing.managers

import it.polito.g26.server.ticketing.experts.Expert
import it.polito.g26.server.ticketing.tickets.Ticket

interface ManagerService {
    fun getManager(email: String): ManagerDTO?
    fun getManagerByDepartment(department: String): List<ManagerDTO>
    fun createManager(manager: Manager)
    fun updateManager(manager: Manager)
    fun assignExpert(ticket: Ticket, expert: Expert)
}