package drewcarlson.coingecko.models.coins

import drewcarlson.coingecko.models.coins.data.Roi
import drewcarlson.coingecko.models.coins.data.SparklineIn7d
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinMarkets(
        @SerialName("id")
        val id: String,
        @SerialName("symbol")
        val symbol: String,
        @SerialName("name")
        val name: String,
        @SerialName("image")
        val image: String? = null,
        @SerialName("current_price")
        val currentPrice: Double = 0.0,
        @SerialName("market_cap")
        val marketCap: Double = 0.0,
        @SerialName("market_cap_rank")
        val marketCapRank: Long = 0,
        @SerialName("total_volume")
        val totalVolume: Double = 0.0,
        @SerialName("high_24h")
        val high24h: Double = 0.0,
        @SerialName("low_24h")
        val low24h: Double = 0.0,
        @SerialName("price_change_24h")
        val priceChange24h: Double = 0.0,
        @SerialName("price_change_percentage_24h")
        val priceChangePercentage24h: Double = 0.0,
        @SerialName("market_cap_change_24h")
        val marketCapChange24h: Double = 0.0,
        @SerialName("market_cap_change_percentage_24h")
        val marketCapChangePercentage24h: Double = 0.0,
        @SerialName("circulating_supply")
        val circulatingSupply: Double = 0.0,
        @SerialName("total_supply")
        val totalSupply: Double? = null,
        @SerialName("ath")
        val ath: Double = 0.0,
        @SerialName("atl")
        val atl: Double = 0.0,
        @SerialName("atl_change_percentage")
        val atlChangePercentage: Double = 0.0,
        @SerialName("ath_change_percentage")
        val athChangePercentage: Double = 0.0,
        @SerialName("atl_date")
        val atlDate: String? = null,
        @SerialName("ath_date")
        val athDate: String? = null,
        @SerialName("roi")
        val roi: Roi? = null,
        @SerialName("last_updated")
        val lastUpdated: String? = null,
        @SerialName("sparkline_in_7d")
        val sparklineIn7d: SparklineIn7d? = null,
        @SerialName("price_change_percentage_1h_in_currency")
        val priceChangePercentage1hInCurrency: Double = 0.0,
        @SerialName("fully_diluted_valuation")
        val fullyDilutedValuation: Long?,
        @SerialName("max_supply")
        val maxSupply: Double = 0.0,
)
