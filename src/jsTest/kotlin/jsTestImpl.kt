package drewcarlson.coingecko

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise


actual fun runBlocking(block: suspend CoroutineScope.() -> Unit): dynamic =
    GlobalScope.promise(block = block)
