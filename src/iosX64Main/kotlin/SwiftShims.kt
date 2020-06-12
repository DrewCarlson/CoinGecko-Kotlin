package drewcarlson.coingecko

import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios

object SwiftShims {
    fun createHttpClient(): HttpClient = HttpClient(Ios)
}