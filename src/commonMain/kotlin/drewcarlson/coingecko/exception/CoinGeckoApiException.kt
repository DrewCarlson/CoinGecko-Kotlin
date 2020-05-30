package drewcarlson.coingecko.exception

import drewcarlson.coingecko.CoinGeckoApiError

class CoinGeckoApiException : RuntimeException {
    var error: CoinGeckoApiError? = null
        private set

    constructor(error: CoinGeckoApiError?) {
        this.error = error
    }

    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?) : super(message, cause)

    override val message: String
        get() = if (error != null) {
            error.toString()
        } else super.message ?: ""

    companion object {
        private const val serialVersionUID = -4298738252483677889L
    }
}