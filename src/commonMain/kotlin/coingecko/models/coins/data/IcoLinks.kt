package coingecko.models.coins.data

import kotlinx.serialization.Serializable

@Serializable
class IcoLinks(
    val web: String? = null,
    val blog: String? = null,
    val github: String? = null,
    val twitter: String? = null,
    val facebook: String? = null,
    val telegram: String? = null,
    val whitepaper: String? = null,
    val linkedin: String? = null,
)
