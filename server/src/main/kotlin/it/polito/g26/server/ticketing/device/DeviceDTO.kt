package it.polito.g26.server.ticketing.device

import it.polito.g26.server.ticketing.ticket.Ticket

data class DeviceDTO (
    val ean: Long,
    val name: String,
    val category: String,
    val brand: String,
    val price: Double,
    val tickets: MutableSet<Ticket>
)

fun Device.toDTO() : DeviceDTO {
    return DeviceDTO(ean!!, name, category, brand, price, tickets)
}