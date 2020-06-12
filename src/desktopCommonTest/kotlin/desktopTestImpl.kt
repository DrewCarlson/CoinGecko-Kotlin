package drewcarlson.coingecko

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.curl.Curl

actual fun createHttpEngine(): HttpClientEngineFactory<*> = Curl