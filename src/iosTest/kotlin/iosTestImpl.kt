package drewcarlson.coingecko

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.ios.Ios

actual fun createHttpEngine(): HttpClientEngineFactory<*> = Ios
