package drewcarlson.coingecko.domain.exchanges

import drewcarlson.coingecko.domain.shared.Ticker
import kotlinx.serialization.Serializable

@Serializable
class ExchangesTickersById(
        val name: String,
        val tickers: List<Ticker> = emptyList()
)