package drewcarlson.coingecko

import drewcarlson.coingecko.models.Ping
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

interface CoinGeckoClient {
    suspend fun ping(): Ping
    suspend fun getPrice(ids: String, vsCurrencies: String): Map<String, Map<String, Double>>
    suspend fun getPrice(
        ids: String, vsCurrencies: String, includeMarketCap: Boolean, include24hrVol: Boolean,
        include24hrChange: Boolean, includeLastUpdatedAt: Boolean
    ): Map<String, Map<String, Double>>

    suspend fun getTokenPrice(
        id: String,
        contractAddress: String,
        vsCurrencies: String
    ): Map<String, Map<String, Double>>

    suspend fun getTokenPrice(
        id: String,
        contractAddress: String,
        vsCurrencies: String,
        includeMarketCap: Boolean = false,
        include24hrVol: Boolean = false,
        include24hrChange: Boolean = false,
        includeLastUpdatedAt: Boolean = false
    ): Map<String, Map<String, Double>>

    suspend fun getSupportedVsCurrencies(): List<String>
    suspend fun getCoinList(): List<CoinList>

    suspend fun getCoinMarkets(vsCurrency: String): List<CoinMarkets>
    suspend fun getCoinMarkets(
        vsCurrency: String,
        ids: String?,
        order: String?,
        perPage: Int?,
        page: Int?,
        sparkline: Boolean,
        priceChangePercentage: String?
    ): List<CoinMarkets>

    suspend fun getCoinById(id: String): CoinFullData
    suspend fun getCoinById(
        id: String,
        localization: Boolean,
        tickers: Boolean,
        marketData: Boolean,
        communityData: Boolean,
        developerData: Boolean,
        sparkline: Boolean
    ): CoinFullData

    suspend fun getCoinTickerById(id: String): CoinTickerById
    suspend fun getCoinTickerById(id: String, exchangeIds: String?, page: Int?, order: String?): CoinTickerById
    suspend fun getCoinHistoryById(id: String, date: String): CoinHistoryById
    suspend fun getCoinHistoryById(id: String, date: String, localization: Boolean): CoinHistoryById
    suspend fun getCoinMarketChartById(id: String, vsCurrency: String, days: Int): MarketChart
    suspend fun getCoinMarketChartRangeById(id: String, vsCurrency: String, from: String, to: String): MarketChart
    suspend fun getCoinStatusUpdateById(id: String): StatusUpdates
    suspend fun getCoinStatusUpdateById(id: String, perPage: Int?, page: Int?): StatusUpdates
    suspend fun getCoinInfoByContractAddress(id: String, contractAddress: String): CoinFullData
    suspend fun getExchanges(): List<Exchanges>
    suspend fun getExchangesList(): List<ExchangesList>

    suspend fun getExchangesById(id: String): Exchanges
    suspend fun getExchangesTickersById(id: String): ExchangesTickersById
    suspend fun getExchangesTickersById(id: String, coinIds: String?, page: Int?, order: String?): ExchangesTickersById
    suspend fun getExchangesStatusUpdatesById(id: String): StatusUpdates
    suspend fun getExchangesStatusUpdatesById(id: String, perPage: Int?, page: Int?): StatusUpdates
    suspend fun getExchangesVolumeChart(id: String, days: Int): List<List<String>>
    suspend fun getStatusUpdates(): StatusUpdates

    suspend fun getStatusUpdates(category: String?, projectType: String?, perPage: Int?, page: Int?): StatusUpdates
    suspend fun getEvents(): Events

    suspend fun getEvents(
        countryCode: String?,
        type: String?,
        page: Int?,
        upcomingEventsOnly: Boolean,
        fromDate: String?,
        toDate: String?
    ): Events

    suspend fun getEventsCountries(): EventCountries
    suspend fun getEventsTypes(): EventTypes
    suspend fun getExchangeRates(): ExchangeRates
    suspend fun getGlobal(): Global
}