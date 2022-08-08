package coingecko.models.search

import coingecko.models.coins.CoinCategory
import coingecko.models.coins.CoinList
import coingecko.models.exchanges.Exchange
import kotlinx.serialization.Serializable

@Serializable
data class SearchResults(
    val coins: List<CoinList>,
    val exchanges: List<Exchange>,
    val categories: List<CoinCategory>,
)
