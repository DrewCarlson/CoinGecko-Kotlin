package drewcarlson.coingecko.models

import kotlinx.serialization.*

@Serializable
data class AssetPlatform(
    val id: String,
    @SerialName("chain_identifier")
    val chainIdentifier: Long? = null,
    val name: String,
    val shortname: String
)
