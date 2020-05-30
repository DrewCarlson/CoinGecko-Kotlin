package drewcarlson.coingecko.domain.exchanges

import kotlinx.serialization.Serializable

@Serializable
data class ExchangesList(
        val id: String,
        val name: String
)