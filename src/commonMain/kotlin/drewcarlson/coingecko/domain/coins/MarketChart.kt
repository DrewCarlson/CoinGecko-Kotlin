package drewcarlson.coingecko.domain.coins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarketChart(
        val prices: List<List<String>>,
        @SerialName("market_caps")
        val marketCaps: List<List<String>>,
        @SerialName("total_volumes")
        val totalVolumes: List<List<String>>
)