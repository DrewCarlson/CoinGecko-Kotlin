package coingecko.models.coins.data

import kotlinx.serialization.Serializable

@Serializable
data class Roi(
    val times: Float = 0f,
    val currency: String? = null,
    val percentage: Float = 0f
)
