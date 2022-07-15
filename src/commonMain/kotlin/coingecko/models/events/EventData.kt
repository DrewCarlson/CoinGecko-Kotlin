package coingecko.models.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventData(
    val type: String,
    val title: String,
    val description: String,
    val organizer: String,
    @SerialName("start_date")
    val startDate: String,
    @SerialName("end_date")
    val endDate: String? = null,
    val website: String? = null,
    val email: String? = null,
    val venue: String? = null,
    val address: String? = null,
    val city: String? = null,
    val country: String,
    val screenshot: String? = null
)
