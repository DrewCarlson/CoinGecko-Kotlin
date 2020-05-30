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

    suspend fun getPrice(
        ids: String,
        vsCurrencies: String,
        includeMarketCap: Boolean = false,
        include24hrVol: Boolean = false,
        include24hrChange: Boolean = false,
        includeLastUpdatedAt: Boolean = false
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

    suspend fun getCoinMarkets(
        vsCurrency: String,
        ids: String? = null,
        order: String? = null,
        perPage: Int? = null,
        page: Int? = null,
        sparkline: Boolean = false,
        priceChangePercentage: String? = null
    ): List<CoinMarkets>

    suspend fun getCoinById(
        id: String,
        localization: Boolean = true,
        tickers: Boolean = false,
        marketData: Boolean = false,
        communityData: Boolean = false,
        developerData: Boolean = false,
        sparkline: Boolean = false
    ): CoinFullData

    suspend fun getCoinTickerById(
        id: String,
        exchangeIds: String? = null,
        page: Int? = null,
        order: String? = null
    ): CoinTickerById

    suspend fun getCoinHistoryById(
        id: String,
        date: String,
        localization: Boolean = false
    ): CoinHistoryById

    suspend fun getCoinMarketChartById(
        id: String,
        vsCurrency: String,
        days: Int
    ): MarketChart

    suspend fun getCoinMarketChartRangeById(
        id: String,
        vsCurrency: String,
        from: String,
        to: String
    ): MarketChart

    suspend fun getCoinStatusUpdateById(
        id: String,
        perPage: Int? = null,
        page: Int? = null
    ): StatusUpdates

    suspend fun getCoinInfoByContractAddress(
        id: String,
        contractAddress: String
    ): CoinFullData

    suspend fun getExchanges(): List<Exchanges>

    suspend fun getExchangesList(): List<ExchangesList>

    suspend fun getExchangesById(id: String): Exchanges

    suspend fun getExchangesTickersById(
        id: String,
        coinIds: String? = null,
        page: Int? = null,
        order: String? = null
    ): ExchangesTickersById

    suspend fun getExchangesStatusUpdatesById(
        id: String,
        perPage: Int? = null,
        page: Int? = null
    ): StatusUpdates

    suspend fun getExchangesVolumeChart(
        id: String,
        days: Int
    ): List<List<String>>

    suspend fun getStatusUpdates(
        category: String? = null,
        projectType: String? = null,
        perPage: Int? = null,
        page: Int? = null
    ): StatusUpdates

    suspend fun getEvents(
        countryCode: String? = null,
        type: String? = null,
        page: Int? = null,
        upcomingEventsOnly: Boolean = false,
        fromDate: String? = null,
        toDate: String? = null
    ): Events

    suspend fun getEventsCountries(): EventCountries

    suspend fun getEventsTypes(): EventTypes

    suspend fun getExchangeRates(): ExchangeRates

    suspend fun getGlobal(): Global
}