package coingecko

import coingecko.constant.Order
import coingecko.error.CoinGeckoApiError
import coingecko.error.CoinGeckoApiException
import coingecko.internal.PagingTransformer
import coingecko.models.*
import coingecko.models.coins.*
import coingecko.models.exchanges.Exchange
import coingecko.models.exchanges.ExchangeId
import coingecko.models.exchanges.ExchangesTickersById
import coingecko.models.global.Global
import coingecko.models.rates.ExchangeRates
import coingecko.models.search.SearchResults
import coingecko.models.search.TrendingCoinList
import coingecko.models.status.StatusUpdates
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.jvm.JvmStatic

private const val IDS = "ids"
private const val PAGE = "page"
private const val PER_PAGE = "per_page"
private const val VS_CURRENCIES = "vs_currencies"
private const val VS_CURRENCY = "vs_currency"
private const val INCLUDE_MARKET_CAP = "include_market_cap"
private const val INCLUDE_24HR_VOL = "include_24hr_vol"
private const val INCLUDE_24HR_CHANGE = "include_24hr_change"
private const val INCLUDE_LAST_UPDATED_AT = "include_last_updated_at"
private const val CONTRACT_ADDRESS = "contract_address"
private const val PRICE_CHANGE_PERCENTAGE = "price_change_percentage"
private const val SPARKLINE = "sparkline"
private const val MARKET_DATA = "market_data"
private const val COMMUNITY_DATA = "community_data"
private const val DEVELOPER_DATA = "developer_data"
private const val LOCALIZATION = "localization"
private const val TICKERS = "tickers"
private const val ORDER = "order"
private const val EXCHANGE_IDS = "exchange_ids"
private const val DATE = "date"
private const val FROM = "from"
private const val TO = "to"
private const val DAYS = "days"
private const val COIN_IDS = "COIN_IDS"

private const val API_HOST = "api.coingecko.com"
private const val API_BASE_PATH = "/api/v3"

typealias RawPriceMap = Map<String, Map<String, String?>>

internal val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
    coerceInputValues = true
    useAlternativeNames = true
}

class CoinGeckoClient(httpClient: HttpClient) {

    constructor() : this(HttpClient())

    private val httpClient = httpClient.config {
        defaultRequest {
            url.protocol = URLProtocol.HTTPS
            url.host = API_HOST
            url.path(API_BASE_PATH, url.encodedPath)
        }
        install(PagingTransformer)
        install(ContentNegotiation) {
            json(json)
        }
    }.apply {
        sendPipeline.intercept(HttpSendPipeline.Before) {
            try {
                proceed()
            } catch (e: Throwable) {
                throw CoinGeckoApiException(e)
            }
        }
    }

    companion object {
        @JvmStatic
        @Deprecated("Use the CoinGeckoClient constructor instead", ReplaceWith("CoinGeckoClient()"))
        fun create(): CoinGeckoClient {
            return CoinGeckoClient()
        }

        @JvmStatic
        @Deprecated("Use the CoinGeckoClient constructor instead", ReplaceWith("CoinGeckoClient(httpClient)"))
        fun create(httpClient: HttpClient): CoinGeckoClient {
            return CoinGeckoClient(httpClient)
        }
    }

    private suspend inline fun <reified T> HttpResponse.bodyOrThrow(): T {
        return if (status == HttpStatusCode.OK) {
            body()
        } else {
            val bodyText = bodyAsText()
            val bodyError = try {
                json.decodeFromString<ErrorBody>(bodyText).error
            } catch (e: SerializationException) {
                null
            }
            throw CoinGeckoApiException(CoinGeckoApiError(status.value, bodyError))
        }
    }

    suspend fun ping(): Ping = httpClient.get("ping").bodyOrThrow()

    suspend fun getPrice(
        ids: String,
        vsCurrencies: String,
        includeMarketCap: Boolean = false,
        include24hrVol: Boolean = false,
        include24hrChange: Boolean = false,
        includeLastUpdatedAt: Boolean = false,
    ): Map<String, CoinPrice> =
        httpClient.get("simple/price") {
            parameter(IDS, ids)
            parameter(VS_CURRENCIES, vsCurrencies)
            parameter(INCLUDE_MARKET_CAP, includeMarketCap)
            parameter(INCLUDE_24HR_VOL, include24hrVol)
            parameter(INCLUDE_24HR_CHANGE, include24hrChange)
            parameter(INCLUDE_LAST_UPDATED_AT, includeLastUpdatedAt)
        }.body<RawPriceMap>().mapValues { (_, v) -> CoinPrice(v) }

    suspend fun getTokenPrice(
        id: String,
        contractAddress: String,
        vsCurrencies: String,
        includeMarketCap: Boolean = false,
        include24hrVol: Boolean = false,
        include24hrChange: Boolean = false,
        includeLastUpdatedAt: Boolean = false,
    ): Map<String, Map<String, Double>> =
        httpClient.get("simple/token_price/$id") {
            parameter(VS_CURRENCIES, vsCurrencies)
            parameter(CONTRACT_ADDRESS, contractAddress)
            parameter(INCLUDE_MARKET_CAP, includeMarketCap)
            parameter(INCLUDE_24HR_VOL, include24hrVol)
            parameter(INCLUDE_24HR_CHANGE, include24hrChange)
            parameter(INCLUDE_LAST_UPDATED_AT, includeLastUpdatedAt)
        }.bodyOrThrow()

    suspend fun getSupportedVsCurrencies(): List<String> =
        httpClient.get("simple/supported_vs_currencies").bodyOrThrow()

    suspend fun getCoinList(includePlatform: Boolean = false): List<CoinList> =
        httpClient.get("coins/list") {
            parameter("include_platform", includePlatform)
        }.bodyOrThrow()

    suspend fun getCoinMarkets(
        vsCurrency: String,
        ids: String? = null,
        order: String? = null,
        perPage: Int? = null,
        page: Int? = null,
        sparkline: Boolean = false,
        priceChangePercentage: String? = null,
    ): CoinMarketsList =
        httpClient.get("coins/markets") {
            parameter(IDS, ids)
            parameter(VS_CURRENCY, vsCurrency)
            parameter(ORDER, order)
            parameter(PER_PAGE, perPage)
            parameter(PAGE, page ?: 1)
            parameter(SPARKLINE, sparkline)
            parameter(PRICE_CHANGE_PERCENTAGE, priceChangePercentage)
        }.bodyOrThrow()

    suspend fun getCoinById(
        id: String,
        localization: Boolean = true,
        tickers: Boolean = false,
        marketData: Boolean = false,
        communityData: Boolean = false,
        developerData: Boolean = false,
        sparkline: Boolean = false,
    ): CoinFullData =
        httpClient.get("coins/$id") {
            parameter(LOCALIZATION, localization)
            parameter(TICKERS, tickers)
            parameter(MARKET_DATA, marketData)
            parameter(COMMUNITY_DATA, communityData)
            parameter(DEVELOPER_DATA, developerData)
            parameter(SPARKLINE, sparkline)
        }.bodyOrThrow()

    suspend fun getCoinTickerById(
        id: String,
        exchangeIds: String? = null,
        page: Int? = null,
        order: String? = null,
    ): CoinTickerById =
        httpClient.get("coins/$id/tickers") {
            parameter(EXCHANGE_IDS, exchangeIds)
            parameter(PAGE, page)
            parameter(ORDER, order)
        }.bodyOrThrow()

    suspend fun getCoinHistoryById(
        id: String,
        date: String,
        localization: Boolean = false,
    ): CoinHistoryById =
        httpClient.get("coins/$id/history") {
            parameter(DATE, date)
            parameter(LOCALIZATION, localization)
        }.bodyOrThrow()

    suspend fun getCoinMarketChartRangeById(
        id: String,
        vsCurrency: String,
        from: String,
        to: String,
    ): MarketChart =
        httpClient.get("coins/$id/market_chart/range") {
            parameter(VS_CURRENCY, vsCurrency)
            parameter(FROM, from)
            parameter(TO, to)
        }.bodyOrThrow()

    suspend fun getCoinMarketChartById(
        id: String,
        vsCurrency: String,
        days: Double,
    ): MarketChart =
        httpClient.get("coins/$id/market_chart") {
            parameter(VS_CURRENCY, vsCurrency)
            parameter(DAYS, days)
        }.bodyOrThrow()

    suspend fun getCoinStatusUpdateById(id: String, perPage: Int? = null, page: Int? = null): StatusUpdates =
        httpClient.get("coins/$id/status_updates") {
            parameter(PAGE, page)
            parameter(PER_PAGE, perPage)
        }.bodyOrThrow()

    suspend fun getCoinInfoByContractAddress(id: String, contractAddress: String): CoinFullData =
        httpClient.get("coins/$id/contract/$contractAddress").bodyOrThrow()

    suspend fun getCoinOhlc(id: String, vsCurrency: String, days: Int): List<CoinOhlc> =
        httpClient.get("coins/$id/ohlc") {
            parameter(VS_CURRENCY, vsCurrency)
            parameter(DAYS, days)
        }.bodyOrThrow()

    suspend fun getAssetPlatforms(): List<AssetPlatform> =
        httpClient.get("asset_platforms").bodyOrThrow()

    suspend fun getCoinCategoriesList(): List<CoinCategory> =
        httpClient.get("coins/categories/list").bodyOrThrow()

    suspend fun getCoinCategories(order: String = Order.MARKET_CAP_DESC): List<CoinCategoryAndData> =
        httpClient.get("coins/categories") {
            parameter(ORDER, order)
        }.bodyOrThrow()

    suspend fun getExchanges(page: Int? = null, perPage: Int? = null): ExchangeList =
        httpClient.get("exchanges") {
            parameter(PAGE, page)
            parameter(PER_PAGE, perPage)
        }.bodyOrThrow()

    suspend fun getExchangesList(): List<ExchangeId> =
        httpClient.get("exchanges/list").bodyOrThrow()

    suspend fun getExchangesById(id: String): Exchange =
        httpClient.get("exchanges/$id").bodyOrThrow()

    suspend fun getExchangesTickersById(
        id: String,
        coinIds: String? = null,
        page: Int? = null,
        order: String? = null,
    ): ExchangesTickersById =
        httpClient.get("exchanges/$id/tickers") {
            parameter(COIN_IDS, coinIds)
            parameter(PAGE, page)
            parameter(ORDER, order)
        }.bodyOrThrow()

    suspend fun getExchangesVolumeChart(id: String, days: Int): List<List<String>> =
        httpClient.get("exchanges/$id/volume_chart") {
            parameter(DAYS, days)
        }.bodyOrThrow()

    suspend fun getExchangeRates(): ExchangeRates =
        httpClient.get("exchange_rates").bodyOrThrow()

    suspend fun getGlobal(): Global =
        httpClient.get("global").bodyOrThrow()

    suspend fun search(query: String): SearchResults =
        httpClient.get("search") {
            parameter("query", query)
        }.bodyOrThrow()

    suspend fun getTrending(): TrendingCoinList =
        httpClient.get("search/trending").bodyOrThrow()

    @Serializable
    private data class ErrorBody(val error: String?)
}
