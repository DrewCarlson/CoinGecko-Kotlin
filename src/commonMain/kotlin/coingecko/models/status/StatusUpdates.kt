package coingecko.models.status

import coingecko.models.Page
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusUpdates(
    @SerialName("status_updates")
    val updates: List<Update> = emptyList(),
    override val total: Int,
    override val perPage: Int,
    override val nextPage: Int?,
    override val previousPage: Int?
) : Page
