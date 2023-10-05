package it.polito.g26.server.ticketing.messages

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import lombok.ToString

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
data class StompMsg(
    val sender: String,
    val receiver: String,
    val chatId: String,
    val message: String,
    val timestamp: String,
    val status: Status
)

enum class Status {
    JOIN,
    MESSAGE,
    LEAVE
}
