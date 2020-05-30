package drewcarlson.coingecko.models.coins.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IcoData(
        @SerialName("ico_start_date")
        val icoStartDate: String? = null,
        @SerialName("ico_end_date")
        val icoEndDate: String? = null,
        @SerialName("short_desc")
        val shortDesc: String? = null,
        @SerialName("description")
        val description: String? = null,
        @SerialName("links")
        val links: IcoLinks? = null,
        @SerialName("softcap_currency")
        val softcapCurrency: String? = null,
        @SerialName("hardcap_currency")
        val hardcapCurrency: String? = null,
        @SerialName("total_raised_currency")
        val totalRaisedCurrency: String? = null,
        @SerialName("softcap_amount")
        val softcapAmount: Double?,
        @SerialName("hardcap_amount")
        val hardcapAmount: Double?,
        @SerialName("total_raised")
        val totalRaised: Double?,
        @SerialName("quote_pre_sale_currency")
        val quotePreSaleCurrency: String? = null,
        @SerialName("base_pre_sale_amount")
        val basePreSaleAmount: Double?,
        @SerialName("quote_pre_sale_amount")
        val quotePreSaleAmount: Double?,
        @SerialName("quote_public_sale_currency")
        val quotePublicSaleCurrency: String? = null,
        @SerialName("base_public_sale_amount")
        val basePublicSaleAmount: String? = null,
        @SerialName("quote_public_sale_amount")
        val quotePublicSaleAmount: String? = null,
        @SerialName("accepting_currencies")
        val acceptingCurrencies: String? = null,
        @SerialName("country_origin")
        val countryOrigin: String? = null,
        @SerialName("pre_sale_start_date")
        val preSaleStartDate: String? = null,
        @SerialName("pre_sale_end_date")
        val preSaleEndDate: String? = null,
        @SerialName("whitelist_url")
        val whitelistUrl: String? = null,
        @SerialName("whitelist_start_date")
        val whitelistStartDate: String? = null,
        @SerialName("whitelist_end_date")
        val whitelistEndDate: String? = null,
        @SerialName("bounty_detail_url")
        val bountyDetailUrl: String? = null,
        @SerialName("amount_for_sale")
        val amountForSale: Double?,
        @SerialName("kyc_required")
        val kycRequired: Boolean = false,
        @SerialName("whitelist_available")
        val whitelistAvailable: Double?,
        @SerialName("pre_sale_available")
        val preSaleAvailable: Double?,
        @SerialName("pre_sale_ended")
        val preSaleEnded: Boolean = false
)