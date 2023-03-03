package coingecko.models.exchanges

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeId(
    val id: String,
    val name: String,
)
