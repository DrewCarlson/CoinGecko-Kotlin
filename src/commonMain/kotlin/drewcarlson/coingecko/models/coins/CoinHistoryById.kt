package drewcarlson.coingecko.models.coins

import drewcarlson.coingecko.models.coins.data.CommunityData
import drewcarlson.coingecko.models.coins.data.DeveloperData
import drewcarlson.coingecko.models.coins.data.PublicInterestStats
import drewcarlson.coingecko.models.shared.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryById(
        @SerialName("id")
        val id: String? = null,
        @SerialName("symbol")
        val symbol: String? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("localization")
        val localization: Map<String, String>? = null,
        @SerialName("image")
        val image: Image? = null,
        @SerialName("market_data")
        val marketData: MarketData? = null,
        @SerialName("community_data")
        val communityData: CommunityData? = null,
        @SerialName("developer_data")
        val developerData: DeveloperData? = null,
        @SerialName("public_interest_stats")
        val publicInterestStats: PublicInterestStats? = null
)