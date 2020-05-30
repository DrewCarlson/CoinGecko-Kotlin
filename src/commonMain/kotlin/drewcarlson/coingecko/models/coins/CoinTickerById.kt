package drewcarlson.coingecko.models.coins

import drewcarlson.coingecko.models.shared.Ticker
import kotlinx.serialization.Serializable

@Serializable
data class CoinTickerById(
        val name: String,
        val tickers: List<Ticker>
)