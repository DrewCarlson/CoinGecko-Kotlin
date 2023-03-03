package coingecko.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ping(
    @SerialName("gecko_says")
    val geckoSays: String,
)
