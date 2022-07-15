package drewcarlson.coingecko.models.exchanges

import kotlinx.serialization.Serializable

@Serializable
data class ExchangesList(
    val id: String,
    val name: String
)
