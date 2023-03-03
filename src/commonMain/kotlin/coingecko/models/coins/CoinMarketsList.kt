package coingecko.models.coins

import coingecko.models.Page
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinMarketsList(
    @SerialName("items")
    val markets: List<CoinMarkets>,
    override val total: Int,
    override val perPage: Int,
    override val nextPage: Int?,
    override val previousPage: Int?,
) : Page
