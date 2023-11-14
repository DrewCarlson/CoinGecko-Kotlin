package coingecko

import coingecko.constant.*
import coingecko.error.*
import io.ktor.client.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlin.test.*
import kotlin.time.Duration.Companion.seconds


class CoinGeckoTests {
    private val lockScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private lateinit var coinGecko: CoinGeckoClient
    private val testLock = Mutex()

    init {
        lockScope.launch {
            while (true) {
                testLock.withLock { delay(5.seconds) }
            }
        }
    }

    @BeforeTest
    fun setup() {
        coinGecko = CoinGeckoClient()
    }

    @AfterTest
    fun cleanup() {
        coinGecko.close()
    }

    @Test
    fun testPing() = runApiTest {
        assertEquals("(V3) To the Moon!", coinGecko.ping().geckoSays)
    }

    @Test
    fun testCoin() = runApiTest { await ->
        val btc = coinGecko.getCoinById("bitcoin", localization = false)
        assertEquals("Bitcoin", btc.name)

        await()
        val eth = coinGecko.getCoinById("ethereum")
        assertEquals("Ethereum", eth.name)

        await()
        val brd = coinGecko.getCoinById("bread")
        assertEquals("Bread", brd.name)
    }

    @Test
    fun testMarketData() = runApiTest {
        val btcData = coinGecko.getCoinMarketChartById("bitcoin", "usd", 3.0)
        assertTrue(btcData.prices.isNotEmpty())
        assertTrue(btcData.prices.first().isNotEmpty())
        assertTrue(btcData.marketCaps.isNotEmpty())
        assertTrue(btcData.marketCaps.first().isNotEmpty())
        assertTrue(btcData.totalVolumes.isNotEmpty())
        assertTrue(btcData.totalVolumes.first().isNotEmpty())
    }

    @Test
    fun testGetCoinMarkets() = runApiTest {
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
    fun testCoinPrice() = runApiTest { await ->
        val btcPrices = coinGecko.getPrice("bitcoin", "usd,cad")
        val btc = assertNotNull(btcPrices["bitcoin"])
        assertNotNull(btc.getPrice("usd"))
        assertNotNull(btc.getPrice("cad"))
        assertNull(btc.lastUpdatedAt)

        await()
        val ethPrices = coinGecko.getPrice(
            "ethereum",
            "usd,eur",
            includeMarketCap = true,
            include24hrVol = true,
            include24hrChange = true,
            includeLastUpdatedAt = true,
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
    fun testCoinTickers() = runApiTest { await ->
        val coinPage1 = coinGecko.getCoinTickerById("tether", "binance")
        assertEquals(100, coinPage1.perPage)
        assertEquals(2, coinPage1.nextPage)
        assertTrue(coinPage1.total > 100)
        assertNull(coinPage1.previousPage)

        await()
        val coinPage2 = coinGecko.getCoinTickerById("tether", "binance", page = 2)
        assertEquals(100, coinPage2.perPage)
        assertEquals(1, coinPage2.previousPage)
        assertTrue(coinPage2.total > 100)
        assertNotNull(coinPage2.nextPage)

        await()
        val coinPage500 = coinGecko.getCoinTickerById("tether", "binance", page = 500)
        assertNull(coinPage500.nextPage)
    }

    @Test
    fun testCoinHistory() = runApiTest {
        val bitcoin = coinGecko.getCoinHistoryById("bitcoin", "23-10-2018")
        val image = assertNotNull(bitcoin.image)
        assertTrue(image.small.isNotBlank())
    }

    @Test
    fun testNonExistentCoin() = runApiTest {
        val exception = assertFails {
            coinGecko.getCoinById("not-a-real-coin")
        }

        assertTrue(
            exception is CoinGeckoApiException,
            "Expected CoinGeckoApiException but was ${exception::class}",
        )
        assertEquals(404, exception.error?.code)
        assertEquals("coin not found", exception.error?.message)
    }

    @Test
    fun testCoinOhlc() = runApiTest {
        val ohlc = coinGecko.getCoinOhlc("tezos", Currency.USD, 1).firstOrNull()

        assertNotNull(ohlc?.time)
        assertNotNull(ohlc?.close)
        assertNotNull(ohlc?.high)
        assertNotNull(ohlc?.low)
        assertNotNull(ohlc?.open)
    }

    @Test
    fun testTrending() = runApiTest {
        val trending = coinGecko.getTrending()

        assertNotNull(trending)
        assertNotNull(trending.coins)
        assertTrue { trending.coins.isNotEmpty() }
        val first = trending.coins.firstOrNull()?.item
        assertNotNull(first?.id)
        assertNotNull(first?.coinId)
        assertNotNull(first?.name)
        assertNotNull(first?.symbol)
        assertNotNull(first?.marketCapRank)
        assertNotNull(first?.thumb)
        assertNotNull(first?.small)
        assertNotNull(first?.large)
        assertNotNull(first?.slug)
        assertNotNull(first?.priceBtc)
        assertNotNull(first?.score)
    }

    @Test
    fun testAssetPlatforms() = runApiTest { await ->
        val assetPlatforms = coinGecko.getAssetPlatforms()

        val withoutId = assetPlatforms.filter { it.id.isEmpty() }
        assertTrue(withoutId.isEmpty(), "All platforms should include an id, ${withoutId.size} were missing")

        val hbar = assetPlatforms.singleOrNull { it.id == "hedera-hashgraph" }
        assertEquals("Hedera Hashgraph", hbar?.name)
        assertEquals(295, hbar?.chainIdentifier)
        assertEquals("hashgraph", hbar?.shortname)

        val poly = assetPlatforms.singleOrNull { it.id == "polygon-pos" }
        assertEquals("Polygon POS", poly?.name)
        assertEquals(137, poly?.chainIdentifier)
        assertEquals("MATIC", poly?.shortname)
    }

    @Test
    fun testDefaultHttpClient() {
        val externalClient = HttpClient()
        val withExternal = CoinGeckoClient(externalClient)
        val withDefault = CoinGeckoClient()

        // Test that default closes
        withDefault.close()
        assertFalse(withDefault.httpClient.isActive)

        // Test that external does not close
        withExternal.close()
        assertTrue(externalClient.isActive)

        // Test that external can be closed
        externalClient.close()
        assertFalse(externalClient.isActive)
    }

    private fun runApiTest(
        testBody: suspend TestScope.(await: suspend () -> Unit) -> Unit,
    ) = runTest(timeout = 30.seconds) {
        testLock.withLock { }
        testBody { testLock.withLock { } }
    }
}
