package drewcarlson.coingecko

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import kotlinx.coroutines.CoroutineScope
import kotlin.test.*

expect fun runBlocking(block: suspend CoroutineScope.() -> Unit)

class CoinGeckoTests {

    private val coinGecko = CoinGeckoService()

    @Test
    fun testPing() = runBlocking {
        assertEquals("(V3) To the Moon!", coinGecko.ping().geckoSays)
    }

    @Test
    fun testCoin() = runBlocking {
        val btc = coinGecko.getCoinById("bitcoin")
        assertEquals("Bitcoin", btc.name)

        val eth = coinGecko.getCoinById("ethereum")
        assertEquals("Ethereum", eth.name)

        val brd = coinGecko.getCoinById("bread")
        assertEquals("Bread", brd.name)
    }

    @Test
    fun testMarketData() = runBlocking {
        val btcData = coinGecko.getCoinMarketChartById("bitcoin", "usd", 3)
        assertTrue(btcData.prices.isNotEmpty())
        assertTrue(btcData.prices.first().isNotEmpty())
        assertTrue(btcData.marketCaps.isNotEmpty())
        assertTrue(btcData.marketCaps.first().isNotEmpty())
        assertTrue(btcData.totalVolumes.isNotEmpty())
        assertTrue(btcData.totalVolumes.first().isNotEmpty())
    }

    @Test
    fun testGetCoinMarkets() = runBlocking {
        val ids = arrayOf("bitcoin", "ethereum", "bread", "zcash")
        val response = coinGecko.getCoinMarkets("usd", ids.joinToString(","))

        assertEquals(ids.size, response.markets.size)
        assertEquals(0, response.total)
        assertEquals(0, response.perPage)
        assertNull(response.nextPage)
        assertNull(response.previousPage)
        response.markets.forEach { market ->
            assertTrue(ids.contains(market.id))
        }
    }

    @Test
    fun testCoinPrice() = runBlocking {
        val btcPrices = coinGecko.getPrice("bitcoin", "usd,cad")
        val btc = assertNotNull(btcPrices["bitcoin"])
        assertNotNull(btc.getPrice("usd"))
        assertNotNull(btc.getPrice("cad"))
        assertNull(btc.lastUpdatedAt)

        val ethPrices = coinGecko.getPrice("ethereum", "usd,eur",
            includeMarketCap = true,
            include24hrVol = true,
            include24hrChange = true,
            includeLastUpdatedAt = true
        )
        val eth = assertNotNull(ethPrices["ethereum"])
        assertNotNull(eth.getPrice("usd"))
        assertNotNull(eth.getPrice("eur"))
        assertNotNull(eth.get24hrChange("usd"))
        assertNotNull(eth.get24hrChange("eur"))
        assertNotNull(eth.get24hrVol("usd"))
        assertNotNull(eth.get24hrVol("eur"))
        assertNotNull(eth.getMarketCap("usd"))
        assertNotNull(eth.getMarketCap("eur"))
        assertNotNull(eth.lastUpdatedAt)
    }

    @Test
    fun testCoinTickers() = runBlocking {
        val coinPage1 = coinGecko.getCoinTickerById("tether", "binance")
        assertEquals(100, coinPage1.perPage)
        assertEquals(2, coinPage1.nextPage)
        assertTrue(coinPage1.total > 100)
        assertNull(coinPage1.previousPage)

        val coinPage2 = coinGecko.getCoinTickerById("tether", "binance", page = 2)
        assertEquals(100, coinPage2.perPage)
        assertEquals(1, coinPage2.previousPage)
        assertTrue(coinPage2.total > 100)
        assertNull(coinPage2.nextPage)
    }
}
