package drewcarlson.coingecko.error

class CoinGeckoApiException : Exception {
    var error: CoinGeckoApiError? = null
        private set

    constructor(error: CoinGeckoApiError?) {
        this.error = error
    }

    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?) : super(message, cause)

    override val message: String
        get() = error?.toString() ?: super.message ?: "<no message>"
}
