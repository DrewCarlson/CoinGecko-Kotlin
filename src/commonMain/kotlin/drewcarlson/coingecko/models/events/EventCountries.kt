package drewcarlson.coingecko.models.events

import kotlinx.serialization.Serializable

@Serializable
data class EventCountries(
    val data: List<EventCountryData> = emptyList(),
    val count: Int
)
