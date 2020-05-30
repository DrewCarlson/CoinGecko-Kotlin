package drewcarlson.coingecko.models.events

import kotlinx.serialization.Serializable


@Serializable
data class EventTypes(
        val data: List<String>,
        val count: Long = 0
)