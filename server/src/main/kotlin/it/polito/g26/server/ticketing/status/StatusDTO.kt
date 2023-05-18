package it.polito.g26.server.ticketing.status

data class StatusDTO(
    val statusId: StatusId?,
    val status: String = ""
)