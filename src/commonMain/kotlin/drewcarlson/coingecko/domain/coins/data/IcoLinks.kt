package drewcarlson.coingecko.domain.coins.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class IcoLinks(
        @SerialName("web")
        val web: String? = null,
        @SerialName("blog")
        val blog: String? = null,
        @SerialName("github")
        val github: String? = null,
        @SerialName("twitter")
        val twitter: String? = null,
        @SerialName("facebook")
        val facebook: String? = null,
        @SerialName("telegram")
        val telegram: String? = null,
        @SerialName("whitepaper")
        val whitepaper: String? = null
)