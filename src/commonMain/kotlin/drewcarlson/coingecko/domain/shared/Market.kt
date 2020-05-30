package drewcarlson.coingecko.domain.shared

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Market(
        val name: String,
        val identifier: String,
        @SerialName("has_trading_incentive")
        val hasTradingIncentive: Boolean = false
)