package drewcarlson.coingecko.models.exchanges

import drewcarlson.coingecko.models.shared.Ticker
import kotlinx.serialization.Serializable

@Serializable
class ExchangesTickersById(
        val name: String,
        val tickers: List<Ticker> = emptyList()
)