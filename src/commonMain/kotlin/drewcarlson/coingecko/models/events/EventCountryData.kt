package drewcarlson.coingecko.models.events

import kotlinx.serialization.Serializable

@Serializable
data class EventCountryData(
    val country: String,
    val code: String
)
