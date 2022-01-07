package drewcarlson.coingecko

import drewcarlson.coingecko.error.CoinGeckoApiError
import drewcarlson.coingecko.error.CoinGeckoApiException
import drewcarlson.coingecko.models.coins.*
import drewcarlson.coingecko.models.events.EventCountries
import drewcarlson.coingecko.models.events.EventTypes
import drewcarlson.coingecko.models.events.Events
import drewcarlson.coingecko.models.exchanges.Exchanges
import drewcarlson.coingecko.models.exchanges.ExchangesList
import drewcarlson.coingecko.models.exchanges.ExchangesTickersById
import drewcarlson.coingecko.models.global.Global
import drewcarlson.coingecko.models.rates.ExchangeRates
import drewcarlson.coingecko.models.status.StatusUpdates
import drewcarlson.coingecko.internal.PagingTransformer
import drewcarlson.coingecko.models.*
import drewcarlson.coingecko.models.search.TrendingCoinList
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
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.native.concurrent.SharedImmutable

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
private const val COUNTRY_CODE = "country_code"
private const val TYPE = "type"
private const val FROM_DATE = "from_date"
private const val TO_DATE = "to_date"
private const val PROJECT_TYPE = "project_type"
private const val UPCOMING_EVENTS_ONLY = "upcoming_events_only"
private const val COIN_IDS = "COIN_IDS"

private const val API_HOST = "api.coingecko.com"
private const val API_BASE_PATH = "/api/v3"

typealias RawPriceMap = Map<String, Map<String, String?>>

@SharedImmutable
internal val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
    coerceInputValues = true
    useAlternativeNames = false
}

internal class CoinGeckoClientImpl(httpClient: HttpClient) : CoinGeckoClient {

    constructor() : this(HttpClient())

    private val httpClient = httpClient.config {
        defaultRequest {
            url.protocol = URLProtocol.HTTPS
            url.host = API_HOST
            url.encodedPath = API_BASE_PATH + url.encodedPath
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

    override suspend fun ping(): Ping = httpClient.get("ping").bodyOrThrow()

    override suspend fun getPrice(
        ids: String,
        vsCurrencies: String,
        includeMarketCap: Boolean,
        include24hrVol: Boolean,
        include24hrChange: Boolean,
        includeLastUpdatedAt: Boolean
    ): Map<String, CoinPrice> =
        httpClient.get("simple/price") {
            parameter(IDS, ids)
            parameter(VS_CURRENCIES, vsCurrencies)
            parameter(INCLUDE_MARKET_CAP, includeMarketCap)
            parameter(INCLUDE_24HR_VOL, include24hrVol)
            parameter(INCLUDE_24HR_CHANGE, include24hrChange)
            parameter(INCLUDE_LAST_UPDATED_AT, includeLastUpdatedAt)
        }.body<RawPriceMap>().mapValues { (_, v) -> CoinPrice(v) }

    override suspend fun getTokenPrice(
        id: String,
        contractAddress: String,
        vsCurrencies: String,
        includeMarketCap: Boolean,
        include24hrVol: Boolean,
        include24hrChange: Boolean,
        includeLastUpdatedAt: Boolean
    ): Map<String, Map<String, Double>> =
        httpClient.get("simple/token_price/$id") {
            parameter(VS_CURRENCIES, vsCurrencies)
            parameter(CONTRACT_ADDRESS, contractAddress)
            parameter(INCLUDE_MARKET_CAP, includeMarketCap)
            parameter(INCLUDE_24HR_VOL, include24hrVol)
            parameter(INCLUDE_24HR_CHANGE, include24hrChange)
            parameter(INCLUDE_LAST_UPDATED_AT, includeLastUpdatedAt)
        }.bodyOrThrow()

    override suspend fun getSupportedVsCurrencies(): List<String> =
        httpClient.get("simple/supported_vs_currencies").bodyOrThrow()

    override suspend fun getCoinList(includePlatform: Boolean): List<CoinList> =
        httpClient.get("coins/list") {
            parameter("include_platform", includePlatform)
        }.bodyOrThrow()

    override suspend fun getCoinMarkets(
        vsCurrency: String,
        ids: String?,
        order: String?,
        perPage: Int?,
        page: Int?,
        sparkline: Boolean,
        priceChangePercentage: String?
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

    override suspend fun getCoinById(
        id: String,
        localization: Boolean,
        tickers: Boolean,
        marketData: Boolean,
        communityData: Boolean,
        developerData: Boolean,
        sparkline: Boolean
    ): CoinFullData =
        httpClient.get("coins/$id") {
            parameter(LOCALIZATION, localization)
            parameter(TICKERS, tickers)
            parameter(MARKET_DATA, marketData)
            parameter(COMMUNITY_DATA, communityData)
            parameter(DEVELOPER_DATA, developerData)
            parameter(SPARKLINE, sparkline)
        }.bodyOrThrow()

    override suspend fun getCoinTickerById(
        id: String,
        exchangeIds: String?,
        page: Int?,
        order: String?
    ): CoinTickerById =
        httpClient.get("coins/$id/tickers") {
            parameter(EXCHANGE_IDS, exchangeIds)
            parameter(PAGE, page)
            parameter(ORDER, order)
        }.bodyOrThrow()

    override suspend fun getCoinHistoryById(
        id: String,
        date: String,
        localization: Boolean
    ): CoinHistoryById =
        httpClient.get("coins/$id/history") {
            parameter(DATE, date)
            parameter(LOCALIZATION, localization)
        }.bodyOrThrow()

    override suspend fun getCoinMarketChartRangeById(
        id: String,
        vsCurrency: String,
        from: String,
        to: String
    ): MarketChart =
        httpClient.get("coins/$id/market_chart/range") {
            parameter(VS_CURRENCY, vsCurrency)
            parameter(FROM, from)
            parameter(TO, to)
        }.bodyOrThrow()

    override suspend fun getCoinMarketChartById(
        id: String,
        vsCurrency: String,
        days: Double
    ): MarketChart =
        httpClient.get("coins/$id/market_chart") {
            parameter(VS_CURRENCY, vsCurrency)
            parameter(DAYS, days)
        }.bodyOrThrow()

    override suspend fun getCoinStatusUpdateById(id: String, perPage: Int?, page: Int?): StatusUpdates =
        httpClient.get("coins/$id/status_updates") {
            parameter(PAGE, page)
            parameter(PER_PAGE, perPage)
        }.bodyOrThrow()

    override suspend fun getCoinInfoByContractAddress(id: String, contractAddress: String): CoinFullData =
        httpClient.get("coins/$id/contract/$contractAddress").bodyOrThrow()

    override suspend fun getCoinOhlc(id: String, vsCurrency: String, days: Int): List<CoinOhlc> =
        httpClient.get("coins/$id/ohlc") {
            parameter(VS_CURRENCY, vsCurrency)
            parameter(DAYS, days)
        }.bodyOrThrow()

    override suspend fun getAssetPlatforms(): List<AssetPlatform> =
        httpClient.get("asset_platforms").bodyOrThrow()

    override suspend fun getCoinCategoriesList(): List<CoinCategory> =
        httpClient.get("coins/categories/list").bodyOrThrow()

    override suspend fun getCoinCategories(order: String): List<CoinCategoryAndData> =
        httpClient.get("coins/categories") {
            parameter(ORDER, order)
        }.bodyOrThrow()

    override suspend fun getExchanges(): List<Exchanges> =
        httpClient.get("exchanges").bodyOrThrow()

    override suspend fun getExchangesList(): List<ExchangesList> =
        httpClient.get("exchanges/list").bodyOrThrow()

    override suspend fun getExchangesById(id: String): Exchanges =
        httpClient.get("exchanges/$id").bodyOrThrow()

    override suspend fun getExchangesTickersById(
        id: String,
        coinIds: String?,
        page: Int?,
        order: String?
    ): ExchangesTickersById =
        httpClient.get("exchanges/$id/tickers") {
            parameter(COIN_IDS, coinIds)
            parameter(PAGE, page)
            parameter(ORDER, order)
        }.bodyOrThrow()

    override suspend fun getExchangesStatusUpdatesById(
        id: String,
        perPage: Int?,
        page: Int?
    ): StatusUpdates =
        httpClient.get("exchanges/$id/status_updates") {
            parameter(PER_PAGE, perPage)
            parameter(PAGE, page)
        }.bodyOrThrow()

    override suspend fun getExchangesVolumeChart(id: String, days: Int): List<List<String>> =
        httpClient.get("exchanges/$id/volume_chart") {
            parameter(DAYS, days)
        }.bodyOrThrow()

    override suspend fun getStatusUpdates(
        category: String?,
        projectType: String?,
        perPage: Int?,
        page: Int?
    ): StatusUpdates =
        httpClient.get("status_updates") {
            parameter(PAGE, page)
            parameter(PER_PAGE, perPage)
            parameter(PROJECT_TYPE, projectType)
        }.bodyOrThrow()

    override suspend fun getEvents(
        countryCode: String?,
        type: String?,
        page: Int?,
        upcomingEventsOnly: Boolean,
        fromDate: String?,
        toDate: String?
    ): Events =
        httpClient.get("events") {
            parameter(COUNTRY_CODE, countryCode)
            parameter(TYPE, type)
            parameter(PAGE, page)
            parameter(UPCOMING_EVENTS_ONLY, upcomingEventsOnly)
            parameter(FROM_DATE, fromDate)
            parameter(TO_DATE, toDate)
        }.bodyOrThrow()

    override suspend fun getEventsCountries(): EventCountries =
        httpClient.get("events/countries").bodyOrThrow()

    override suspend fun getEventsTypes(): EventTypes =
        httpClient.get("events/types").bodyOrThrow()

    override suspend fun getExchangeRates(): ExchangeRates =
        httpClient.get("exchange_rates").bodyOrThrow()

    override suspend fun getGlobal(): Global =
        httpClient.get("global").bodyOrThrow()

    override suspend fun getTrending(): TrendingCoinList =
        httpClient.get("search/trending").bodyOrThrow()

    @Serializable
    private data class ErrorBody(val error: String?)
}
