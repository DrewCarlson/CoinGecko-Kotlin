package drewcarlson.coingecko

import drewcarlson.coingecko.domain.Ping
import drewcarlson.coingecko.domain.coins.*
import drewcarlson.coingecko.domain.events.EventCountries
import drewcarlson.coingecko.domain.events.EventTypes
import drewcarlson.coingecko.domain.events.Events
import drewcarlson.coingecko.domain.exchanges.Exchanges
import drewcarlson.coingecko.domain.exchanges.ExchangesList
import drewcarlson.coingecko.domain.exchanges.ExchangesTickersById
import drewcarlson.coingecko.domain.global.Global
import drewcarlson.coingecko.domain.rates.ExchangeRates
import drewcarlson.coingecko.domain.status.StatusUpdates
import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.takeFrom
import kotlinx.serialization.json.Json

private const val IDS = "ids"
private const val PAGE = "page"
private const val PER_PAGE = "per_page"
private const val VS_CURRENCIES = "vs_currencies"
private const val VS_CURRENCY = "vs_currency"
private const val INCLUDE_MARKET_CAP = "include_market_cap"
private const val INCLUDE_24HR_VOL = "include_24hr_vol"
private const val INCLUDE_24HR_CHANGE = "include_24hr_change"
private const val INCLUDE_LAST_UPDATED_AT = "include_last_updated_at"
private const val CONTRACT_ADDRESS = "contract_addresses"
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


class CoinGeckoService(
    httpClient: HttpClient
) : CoinGeckoClient {
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        this.encodeDefaults
    }
    private val httpClient = httpClient.config {
        defaultRequest {
            url.host = "api.coingecko.com"
            url.encodedPath = "/api/v3" + url.encodedPath
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
    }

    override suspend fun ping(): Ping = httpClient.get("ping")

    override suspend fun getPrice(
        ids: String,
        vsCurrencies: String
    ): Map<String, Map<String, Double>> =
        httpClient.get("simple/price") {
            parameter(IDS, ids)
            parameter(VS_CURRENCIES, vsCurrencies)
        }

    override suspend fun getPrice(
        ids: String,
        vsCurrencies: String,
        includeMarketCap: Boolean,
        include24hrVol: Boolean,
        include24hrChange: Boolean,
        includeLastUpdatedAt: Boolean
    ): Map<String, Map<String, Double>> =
        httpClient.get("simple/price") {
            parameter(IDS, ids)
            parameter(VS_CURRENCIES, vsCurrencies)
            parameter(INCLUDE_MARKET_CAP, includeMarketCap)
            parameter(INCLUDE_24HR_VOL, include24hrVol)
            parameter(INCLUDE_24HR_CHANGE, include24hrChange)
            parameter(INCLUDE_LAST_UPDATED_AT, includeLastUpdatedAt)
        }

    override suspend fun getTokenPrice(
        id: String,
        contractAddress: String,
        vsCurrencies: String
    ): Map<String, Map<String, Double>> =
        httpClient.get("simple/token_price/$id") {
            parameter(CONTRACT_ADDRESS, contractAddress)
            parameter(VS_CURRENCIES, vsCurrencies)
        }

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
            parameter(INCLUDE_MARKET_CAP, includeMarketCap)
            parameter(INCLUDE_24HR_VOL, include24hrVol)
            parameter(INCLUDE_24HR_CHANGE, include24hrChange)
            parameter(INCLUDE_LAST_UPDATED_AT, includeLastUpdatedAt)
        }

    override suspend fun getSupportedVsCurrencies(): List<String> =
        httpClient.get("simple/supported_vs_currencies")

    override suspend fun getCoinList(): List<CoinList> =
        httpClient.get("coins/list")

    override suspend fun getCoinMarkets(vsCurrency: String): List<CoinMarkets> =
        httpClient.get("coins/markets") {
            parameter(VS_CURRENCY, vsCurrency)
        }

    override suspend fun getCoinMarkets(
        vsCurrency: String,
        ids: String?,
        order: String?,
        perPage: Int?,
        page: Int?,
        sparkline: Boolean,
        priceChangePercentage: String?
    ): List<CoinMarkets> =
        httpClient.get("coins/markets") {
            parameter(IDS, ids)
            parameter(VS_CURRENCY, vsCurrency)
            parameter(ORDER, order)
            parameter(PER_PAGE, perPage)
            parameter(PAGE, page)
            parameter(SPARKLINE, sparkline)
            parameter(PRICE_CHANGE_PERCENTAGE, priceChangePercentage)
        }

    override suspend fun getCoinById(id: String): CoinFullData =
        httpClient.get("coins/$id")

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
        }

    override suspend fun getCoinTickerById(id: String): CoinTickerById =
        httpClient.get("coins/$id/tickers")

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
        }

    override suspend fun getCoinHistoryById(id: String, date: String): CoinHistoryById =
        httpClient.get("coins/$id/history") {
            parameter(DATE, date)
        }

    override suspend fun getCoinHistoryById(
        id: String,
        date: String,
        localization: Boolean
    ): CoinHistoryById =
        httpClient.get("coins/$id/history") {
            parameter(DATE, date)
            parameter(LOCALIZATION, localization)
        }


    override suspend fun getCoinMarketChartRangeById(
        id: String,
        vsCurrency: String,
        from: String,
        to: String
    ): MarketChart =
        httpClient.get("coins/$id/market_chart") {
            parameter(VS_CURRENCY, vsCurrency)
            parameter(FROM, from)
            parameter(TO, to)
        }

    override suspend fun getCoinMarketChartById(
        id: String,
        vsCurrency: String,
        days: Int
    ): MarketChart =
        httpClient.get("coins/$id/market_chart") {
            parameter(VS_CURRENCY, vsCurrency)
            parameter(DAYS, days)
        }

    // coins/{id}/status_updates
    override suspend fun getCoinStatusUpdateById(id: String): StatusUpdates {
        TODO("Not yet implemented")
    }

    // coins/{id}/status_updates
    override suspend fun getCoinStatusUpdateById(id: String, perPage: Int?, page: Int?): StatusUpdates {
        TODO("Not yet implemented")
    }

    // coins/{id}/contract/{contract_address}
    override suspend fun getCoinInfoByContractAddress(id: String, contractAddress: String): CoinFullData {
        TODO("Not yet implemented")
    }

    override suspend fun getExchanges(): List<Exchanges> =
        httpClient.get("exchanges")

    // exchanges/list
    override suspend fun getExchangesList(): List<ExchangesList> {
        TODO("Not yet implemented")
    }

    // exchanges/{id}
    override suspend fun getExchangesById(id: String): Exchanges {
        TODO("Not yet implemented")
    }

    // exchanges/{id}/tickers
    override suspend fun getExchangesTickersById(id: String): ExchangesTickersById {
        TODO("Not yet implemented")
    }

    // exchanges/{id}/tickers
    override suspend fun getExchangesTickersById(
        id: String,
        coinIds: String?,
        page: Int?,
        order: String?
    ): ExchangesTickersById {
        TODO("Not yet implemented")
    }

    // exchanges/{id}/status_updates
    override suspend fun getExchangesStatusUpdatesById(id: String): StatusUpdates {
        TODO("Not yet implemented")
    }

    // exchanges/{id}/status_updates
    override suspend fun getExchangesStatusUpdatesById(id: String, perPage: Int?, page: Int?): StatusUpdates {
        TODO("Not yet implemented")
    }

    // exchanges/{id}/volume_chart
    override suspend fun getExchangesVolumeChart(id: String, days: Int): List<List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun getStatusUpdates(): StatusUpdates =
        httpClient.get("status_updates")

    // status_updates
    override suspend fun getStatusUpdates(
        category: String?,
        projectType: String?,
        perPage: Int?,
        page: Int?
    ): StatusUpdates {
        TODO("Not yet implemented")
    }

    override suspend fun getEvents(): Events =
        httpClient.get("events")

    // events
    override suspend fun getEvents(
        countryCode: String?,
        type: String?,
        page: Int?,
        upcomingEventsOnly: Boolean,
        fromDate: String?,
        toDate: String?
    ): Events {
        TODO("Not yet implemented")
    }

    override suspend fun getEventsCountries(): EventCountries =
        httpClient.get("events/countries")

    override suspend fun getEventsTypes(): EventTypes =
        httpClient.get("events/types")

    override suspend fun getExchangeRates(): ExchangeRates =
        httpClient.get("exchange_rates")

    override suspend fun getGlobal(): Global =
        httpClient.get("global")
}
