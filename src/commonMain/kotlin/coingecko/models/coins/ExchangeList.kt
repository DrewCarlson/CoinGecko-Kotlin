package coingecko.models.coins

import coingecko.models.Page
import coingecko.models.exchanges.Exchange
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeList(
    @SerialName("items")
    val exchanges: List<Exchange>,
    override val total: Int,
    override val perPage: Int,
    override val nextPage: Int?,
    override val previousPage: Int?,
) : Page
