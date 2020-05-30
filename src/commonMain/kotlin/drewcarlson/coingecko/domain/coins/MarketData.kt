package drewcarlson.coingecko.domain.coins

import drewcarlson.coingecko.domain.coins.data.Roi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarketData(
        @SerialName("current_price")
        val currentPrice: Map<String, Double>? = null,
        @SerialName("roi")
        val roi: Roi? = null,
        @SerialName("ath")
        val ath: Map<String, Double>? = null,
        @SerialName("ath_change_percentage")
        val athChangePercentage: Map<String, Double>? = null,
        @SerialName("ath_date")
        val athDate: Map<String, String>? = null,
        @SerialName("market_cap")
        val marketCap: Map<String, Double>? = null,
        @SerialName("market_cap_rank")
        val marketCapRank: Long = 0,
        @SerialName("total_volume")
        val totalVolume: Map<String, Double>? = null,
        @SerialName("high_24h")
        val high24h: Map<String, Double>? = null,
        @SerialName("low_24h")
        val low24h: Map<String, Double>? = null,
        @SerialName("price_change_24h")
        val priceChange24h: Double = 0.0,
        @SerialName("price_change_percentage_24h")
        val priceChangePercentage24h: Double = 0.0,
        @SerialName("price_change_percentage_7d")
        val priceChangePercentage7d: Double = 0.0,
        @SerialName("price_change_percentage_14d")
        val priceChangePercentage14d: Double = 0.0,
        @SerialName("price_change_percentage_30d")
        val priceChangePercentage30d: Double = 0.0,
        @SerialName("price_change_percentage_60d")
        val priceChangePercentage60d: Double = 0.0,
        @SerialName("price_change_percentage_200d")
        val priceChangePercentage200d: Double = 0.0,
        @SerialName("price_change_percentage_1y")
        val priceChangePercentage1y: Double = 0.0,
        @SerialName("market_cap_change_24h")
        val marketCapChange24h: Double = 0.0,
        @SerialName("market_cap_change_percentage_24h")
        val marketCapChangePercentage24h: Double = 0.0,
        @SerialName("price_change_24h_in_currency")
        val priceChange24hInCurrency: Map<String, Double>? = null,
        @SerialName("price_change_percentage_1h_in_currency")
        val priceChangePercentage1hInCurrency: Map<String, Double>? = null,
        @SerialName("price_change_percentage_24h_in_currency")
        val priceChangePercentage24hInCurrency: Map<String, Double>? = null,
        @SerialName("price_change_percentage_7d_in_currency")
        val priceChangePercentage7dInCurrency: Map<String, Double>? = null,
        @SerialName("price_change_percentage_14d_in_currency")
        val priceChangePercentage14dInCurrency: Map<String, Double>? = null,
        @SerialName("price_change_percentage_30d_in_currency")
        val priceChangePercentage30dInCurrency: Map<String, Double>? = null,
        @SerialName("price_change_percentage_60d_in_currency")
        val priceChangePercentage60dInCurrency: Map<String, Double>? = null,
        @SerialName("price_change_percentage_200d_in_currency")
        val priceChangePercentage200dInCurrency: Map<String, Double>? = null,
        @SerialName("price_change_percentage_1y_in_currency")
        val priceChangePercentage1yInCurrency: Map<String, Double>? = null,
        @SerialName("market_cap_change_24h_in_currency")
        val marketCapChange24hInCurrency: Map<String, Double>? = null,
        @SerialName("market_cap_change_percentage_24h_in_currency")
        val marketCapChangePercentage24hInCurrency: Map<String, Double>? = null,
        @SerialName("total_supply")
        val totalSupply: Double?,
        @SerialName("circulating_supply")
        val circulatingSupply: Double = 0.0,
        @SerialName("last_updated")
        val lastUpdated: String? = null
)