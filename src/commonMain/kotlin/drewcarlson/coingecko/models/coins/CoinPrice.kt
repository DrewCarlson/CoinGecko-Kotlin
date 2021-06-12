package drewcarlson.coingecko.models.coins


data class CoinPrice(
    private val rawFields: Map<String, String?>
) {
    val lastUpdatedAt: Long? = rawFields["last_updated_at"]?.toLongOrNull()

    fun getPrice(currency: String): Double =
        checkNotNull(getPriceOrNull(currency))

    fun getPriceOrNull(currency: String): Double? =
        rawFields[currency.lowercase()]?.toDoubleOrNull()

    fun getMarketCap(currency: String): Double =
        checkNotNull(getMarketCapOrNull(currency))

    fun getMarketCapOrNull(currency: String): Double? =
        rawFields["${currency.lowercase()}_market_cap"]?.toDoubleOrNull()

    fun get24hrVol(currency: String): Double =
        checkNotNull(get24hrVolOrNull(currency))

    fun get24hrVolOrNull(currency: String): Double? =
        rawFields["${currency.lowercase()}_24h_vol"]?.toDoubleOrNull()

    fun get24hrChange(currency: String): Double =
        checkNotNull(get24hrChangeOrNull(currency))

    fun get24hrChangeOrNull(currency: String): Double? =
        rawFields["${currency.lowercase()}_24h_change"]?.toDoubleOrNull()

    fun getRawField(key: String): String? = rawFields[key]

    override fun toString(): String = rawFields.toString()
}
