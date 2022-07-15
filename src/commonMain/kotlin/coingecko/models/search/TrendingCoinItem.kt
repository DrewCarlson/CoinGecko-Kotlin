package coingecko.models.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingCoinItem(
    val id: String,
    @SerialName("coin_id")
    val coinId: Int,
    val name: String,
    val symbol: String,
    @SerialName("market_cap_rank")
    val marketCapRank: Int,
    val thumb: String,
    val small: String,
    val large: String,
    val slug: String,
    @SerialName("price_btc")
    val priceBtc: Double,
    val score: Int
)
