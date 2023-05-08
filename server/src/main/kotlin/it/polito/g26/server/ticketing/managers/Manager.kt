package it.polito.g26.server.ticketing.managers

import it.polito.g26.server.ticketing.experts.Expert
import it.polito.g26.server.ticketing.tickets.Ticket
import it.polito.g26.server.users.Role
import it.polito.g26.server.users.User
import jakarta.persistence.*
import org.hibernate.mapping.Join

@Entity
@Table(name = "manager")
class Manager(
    @ManyToMany
    @JoinTable(name = "manager_expert",
        joinColumns = [JoinColumn(name = "manager_id")],
        inverseJoinColumns = [JoinColumn(name = "expert_id")])
    var experts: MutableSet<Expert> = mutableSetOf(),
    @OneToMany(mappedBy = "manager")
    var tickets: MutableSet<Ticket> = mutableSetOf(),
    override var role: Role = Role.MANAGER
): User()