package drewcarlson.coingecko

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.curl.Curl
import kotlinx.coroutines.*

actual fun runBlocking(block: suspend CoroutineScope.() -> Unit) =
    kotlinx.coroutines.runBlocking(block = block)

actual fun createHttpEngine(): HttpClientEngineFactory<*> = Curl
