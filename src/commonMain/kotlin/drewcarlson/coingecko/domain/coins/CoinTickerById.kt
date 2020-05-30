package drewcarlson.coingecko.domain.coins

import drewcarlson.coingecko.domain.shared.Ticker
import kotlinx.serialization.Serializable

@Serializable
data class CoinTickerById(
        val name: String,
        val tickers: List<Ticker>
)