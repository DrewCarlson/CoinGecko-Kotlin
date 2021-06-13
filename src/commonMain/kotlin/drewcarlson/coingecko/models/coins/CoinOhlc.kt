package drewcarlson.coingecko.models.coins

import drewcarlson.coingecko.internal.*
import kotlinx.serialization.Serializable

@Serializable(with = CoinOhlcSerializer::class)
data class CoinOhlc(
    val time: Long,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
)
