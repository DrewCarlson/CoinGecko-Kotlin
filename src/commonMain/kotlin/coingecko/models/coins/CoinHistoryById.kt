package coingecko.models.coins

import coingecko.models.coins.data.CommunityData
import coingecko.models.coins.data.DeveloperData
import coingecko.models.coins.data.PublicInterestStats
import coingecko.models.shared.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryById(
    val id: String,
    val symbol: String,
    val name: String,
    val localization: Map<String, String> = emptyMap(),
    val image: Image? = null,
    @SerialName("market_data")
    val marketData: MarketData? = null,
    @SerialName("community_data")
    val communityData: CommunityData? = null,
    @SerialName("developer_data")
    val developerData: DeveloperData? = null,
    @SerialName("public_interest_stats")
    val publicInterestStats: PublicInterestStats? = null,
)
