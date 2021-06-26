package drewcarlson.coingecko

import kotlinx.coroutines.*

// https://github.com/Kotlin/kotlinx.coroutines/issues/770
actual fun runBlocking(block: suspend CoroutineScope.() -> Unit) {
}

