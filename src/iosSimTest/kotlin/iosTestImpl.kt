package drewcarlson.coingecko

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.ios.Ios
import kotlinx.coroutines.*

actual fun runBlocking(block: suspend CoroutineScope.() -> Unit) {
    // TODO: ktor client calls hang in runBlocking.
}

actual fun createHttpEngine(): HttpClientEngineFactory<*> = Ios
