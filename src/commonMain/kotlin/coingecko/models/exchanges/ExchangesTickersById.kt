package coingecko.models.exchanges

import coingecko.models.Page
import coingecko.models.shared.Ticker
import kotlinx.serialization.Serializable

@Serializable
class ExchangesTickersById(
    val name: String,
    val tickers: List<Ticker> = emptyList(),
    override val total: Int,
    override val perPage: Int,
    override val nextPage: Int?,
    override val previousPage: Int?,
) : Page
