package drewcarlson.coingecko

import kotlinx.serialization.SerialName

data class CoinGeckoApiError(
        val code: Int = 0,
        @SerialName("error")
        val message: String? = null
)