package coingecko.models

import coingecko.internal.AssetPlatformSerializer
import kotlinx.serialization.*

@Serializable(with = AssetPlatformSerializer::class)
data class AssetPlatform(
    val id: String,
    @SerialName("chain_identifier")
    val chainIdentifier: Long? = null,
    val name: String,
    val shortname: String,
)
