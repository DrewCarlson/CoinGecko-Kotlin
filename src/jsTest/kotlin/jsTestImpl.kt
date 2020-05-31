package drewcarlson.coingecko

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.Js
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise


actual fun runBlocking(block: suspend CoroutineScope.() -> Unit): dynamic =
    GlobalScope.promise(block = block)

actual fun createHttpEngine(): HttpClientEngineFactory<*> = Js