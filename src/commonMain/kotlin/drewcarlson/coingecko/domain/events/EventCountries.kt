package drewcarlson.coingecko.domain.events

import kotlinx.serialization.Serializable


@Serializable
data class EventCountries(
        val data: List<EventCountryData> = emptyList(),
        val count: Int
)