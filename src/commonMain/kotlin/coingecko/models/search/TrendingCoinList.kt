package coingecko.models.search

import kotlinx.serialization.Serializable

@Serializable
data class TrendingCoinList(
    val coins: List<TrendingCoin>
)
