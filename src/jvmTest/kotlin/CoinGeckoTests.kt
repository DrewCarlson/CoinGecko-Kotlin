package drewcarlson.coingecko

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CoinGeckoTests {

    val coinGecko = CoinGeckoService(HttpClient(OkHttp))

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
}