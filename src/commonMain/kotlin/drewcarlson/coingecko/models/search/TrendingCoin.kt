package drewcarlson.coingecko.models.search

import kotlinx.serialization.Serializable

@Serializable
data class TrendingCoin(
    val item: TrendingCoinItem
)
