package drewcarlson.coingecko.models.shared

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ticker(
        val base: String,
        val target: String,
        val market: Market,
        val last: Double = 0.0,
        val volume: Double = 0.0,
        @SerialName("converted_last")
        val convertedLast: Map<String, String> = emptyMap(),
        @SerialName("converted_volume")
        val convertedVolume: Map<String, String> = emptyMap(),
        @SerialName("trust_score")
        val trustScore: String?,
        @SerialName("bid_ask_spread_percentage")
        val bidAskSpreadPercentage: Double?,
        val timestamp: String,
        @SerialName("last_traded_at")
        val lastTradedAt: String,
        @SerialName("last_fetch_at")
        val lastFetchAt: String,
        @SerialName("is_anomaly")
        val isAnomaly: Boolean = false,
        @SerialName("is_stale")
        val isStale: Boolean = false,
        @SerialName("trade_url")
        val tradeUrl: String? = null,
        @SerialName("coin_id")
        val coinId: String
)