package drewcarlson.coingecko.domain.coins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinList(
        @SerialName("id")
        val id: String,
        @SerialName("symbol")
        val symbol: String,
        @SerialName("name")
        val name: String
)