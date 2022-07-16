package coingecko.models.coins

import coingecko.internal.NullValueOmittingListSerializer
import coingecko.models.coins.data.*
import coingecko.models.shared.Image
import coingecko.models.shared.Ticker
import coingecko.models.status.Update
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinFullData(
    val id: String,
    val symbol: String,
    val name: String,
    @SerialName("hashing_algorithm")
    val hashingAlgorithm: String? = null,
    @SerialName("block_time_in_minutes")
    val blockTimeInMinutes: Long = 0,
    @Serializable(NullValueOmittingListSerializer::class)
    val categories: List<String> = emptyList(),
    val localization: Map<String, String> = emptyMap(),
    val description: Map<String, String> = emptyMap(),
    val links: Links,
    val image: Image,
    @SerialName("country_origin")
    val countryOrigin: String? = null,
    @SerialName("genesis_date")
    val genesisDate: String? = null,
    @SerialName("contract_address")
    val contractAddress: String? = null,
    @SerialName("ico_data")
    val icoData: IcoData? = null,
    @SerialName("market_cap_rank")
    val marketCapRank: Long = 0,
    @SerialName("coingecko_rank")
    val coingeckoRank: Long = 0,
    @SerialName("coingecko_score")
    val coingeckoScore: Double = 0.0,
    @SerialName("developer_score")
    val developerScore: Double = 0.0,
    @SerialName("community_score")
    val communityScore: Double = 0.0,
    @SerialName("liquidity_score")
    val liquidityScore: Double = 0.0,
    @SerialName("public_interest_score")
    val publicInterestScore: Double = 0.0,
    @SerialName("market_data")
    val marketData: MarketData? = null,
    @SerialName("community_data")
    val communityData: CommunityData? = null,
    @SerialName("developer_data")
    val developerData: DeveloperData? = null,
    @SerialName("public_interest_stats")
    val publicInterestStats: PublicInterestStats? = null,
    @SerialName("status_updates")
    val statusUpdates: List<Update>? = null,
    @SerialName("last_updated")
    val lastUpdated: String? = null,
    val tickers: List<Ticker>? = null,
    @SerialName("sentiment_votes_up_percentage")
    val sentimentVotesUpPercentage: Float = 50f,
    @SerialName("sentiment_votes_down_percentage")
    val sentimentVotesDownPercentage: Float = 50f,
    @SerialName("asset_platform_id")
    val assetPlatformId: String?,
    val platforms: Map<String, String> = emptyMap(),
    @SerialName("public_notice")
    val publicNotice: String? = null,
    @SerialName("additional_notices")
    val additionalNotices: List<String> = emptyList(),
)
