package drewcarlson.coingecko.domain.status

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class StatusUpdates(
        @SerialName("status_updates")
        val updates: List<Update> = emptyList()
)