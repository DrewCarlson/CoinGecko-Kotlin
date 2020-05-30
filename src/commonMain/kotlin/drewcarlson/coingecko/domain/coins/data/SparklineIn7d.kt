package drewcarlson.coingecko.domain.coins.data

import kotlinx.serialization.Serializable

@Serializable
data class SparklineIn7d(
        val price: List<Double>? = null
)