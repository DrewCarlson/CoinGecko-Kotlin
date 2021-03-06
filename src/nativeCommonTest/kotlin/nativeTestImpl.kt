package drewcarlson.coingecko

import com.autodesk.coroutineworker.CoroutineWorker
import kotlinx.coroutines.*
import platform.Foundation.*
import kotlin.coroutines.CoroutineContext

// Patch for https://github.com/Kotlin/kotlinx.coroutines/issues/770
actual fun runBlocking(block: suspend CoroutineScope.() -> Unit) {
    var completed = false

    GlobalScope.launch(MainRunLoopDispatcher) {
        CoroutineWorker.execute(block)
        completed = true
    }

    while (!completed) advanceRunLoop()
}

private object MainRunLoopDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        NSRunLoop.mainRunLoop().performBlock { block.run() }
    }
}

private fun advanceRunLoop() {
    NSRunLoop.mainRunLoop.runUntilDate(
        limitDate = NSDate().addTimeInterval(1.0) as NSDate
    )
}
