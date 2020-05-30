package drewcarlson.coingecko.domain.global

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GlobalData(
        @SerialName("active_cryptocurrencies")
        val activeCryptocurrencies: Long = 0,
        @SerialName("upcoming_icos")
        val upcomingIcos: Long = 0,
        @SerialName("ongoing_icos")
        val ongoingIcos: Long = 0,
        @SerialName("ended_icos")
        val endedIcos: Long = 0,
        @SerialName("markets")
        val markets: Long = 0,
        @SerialName("total_market_cap")
        val totalMarketCap: Map<String, Double>? = null,
        @SerialName("total_volume")
        val totalVolume: Map<String, Double>? = null,
        @SerialName("market_cap_percentage")
        val marketCapPercentage: Map<String, Double>? = null,
        @SerialName("market_cap_change_percentage_24h_usd")
        val marketCapChangePercentage24hUsd: Double = 0.0,
        @SerialName("updated_at")
        val updatedAt: Long = 0
)