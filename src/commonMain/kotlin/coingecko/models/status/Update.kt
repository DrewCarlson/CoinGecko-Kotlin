package coingecko.models.status

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Update(
    val description: String? = null,
    val category: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    val user: String? = null,
    @SerialName("user_title")
    val userTitle: String? = null,
    val pin: Boolean = false,
    val project: Project? = null
)
