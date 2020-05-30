package drewcarlson.coingecko.models.coins.data

import kotlinx.serialization.Serializable

@Serializable
data class SparklineIn7d(
        val price: List<Double>? = null
)