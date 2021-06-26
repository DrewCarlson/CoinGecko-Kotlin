package drewcarlson.coingecko.internal

import drewcarlson.coingecko.error.*
import drewcarlson.coingecko.json
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

internal object ErrorTransformer : HttpClientFeature<ErrorTransformer, ErrorTransformer> {

    override val key: AttributeKey<ErrorTransformer> = AttributeKey("ErrorTransformer")

    override fun prepare(block: ErrorTransformer.() -> Unit): ErrorTransformer = this

    override fun install(feature: ErrorTransformer, scope: HttpClient) {
        scope.requestPipeline.intercept(HttpRequestPipeline.Render) {
            try {
                proceed()
            } catch (e: Throwable) {
                if (e is ResponseException) {
                    val bodyText = e.response.readText()
                    val body = try {
                        json.parseToJsonElement(bodyText)
                    } catch (e: SerializationException) {
                        null
                    }
                    throw CoinGeckoApiException(
                        CoinGeckoApiError(
                            code = e.response.status.value,
                            message = body?.jsonObject
                                ?.get("error")
                                ?.jsonPrimitive
                                ?.contentOrNull
                                ?: bodyText
                        )
                    )
                } else {
                    throw CoinGeckoApiException(cause = e)
                }
            }
        }
    }
}
