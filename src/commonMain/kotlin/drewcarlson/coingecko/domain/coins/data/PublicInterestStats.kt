package drewcarlson.coingecko.domain.coins.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PublicInterestStats(
        @SerialName("alexa_rank")
        val alexaRank: Long = 0,
        @SerialName("bing_matches")
        val bingMatches: Long = 0
)