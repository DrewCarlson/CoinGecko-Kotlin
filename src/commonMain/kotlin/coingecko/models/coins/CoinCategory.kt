package coingecko.models.coins

import kotlinx.serialization.*
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class CoinCategory(
    @JsonNames("category_id", "id")
    val categoryId: String,
    val name: String,
)

@Serializable
data class CoinCategoryAndData(
    val id: String,
    val name: String,
    @SerialName("market_cap")
    val marketCap: Double,
    @SerialName("market_cap_change_24h")
    val marketCapChange24h: Double,
    @SerialName("updated_at")
    val updatedAt: String,
)
