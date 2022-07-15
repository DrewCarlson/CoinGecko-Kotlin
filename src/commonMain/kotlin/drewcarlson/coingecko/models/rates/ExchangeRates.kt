package drewcarlson.coingecko.models.rates

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRates(
    val rates: Map<String, Rate>
)
