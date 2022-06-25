package drewcarlson.coingecko.internal

import io.ktor.client.HttpClient
import io.ktor.client.plugins.*
import io.ktor.client.statement.HttpResponseContainer
import io.ktor.client.statement.HttpResponsePipeline
import io.ktor.util.AttributeKey
import io.ktor.utils.io.ByteReadChannel

private const val TOTAL = "total"
private const val PAGE = "page"
private const val PER_PAGE = "per-page"
private const val ARRAY_START = "["

/**
 * Extract pagination information from response headers and merge
 * with the JSON body for use with [drewcarlson.coingecko.models.Page]
 * response models.
 *
 * If the body is an array, an object will be returned and the
 * body moved to an `items` field.
 */
internal object PagingTransformer : HttpClientPlugin<PagingTransformer, PagingTransformer> {

    override val key: AttributeKey<PagingTransformer> = AttributeKey("PagingTransformer")

    override fun prepare(block: PagingTransformer.() -> Unit): PagingTransformer = this

    override fun install(plugin: PagingTransformer, scope: HttpClient) {
        scope.responsePipeline.intercept(HttpResponsePipeline.Parse) { (info, body) ->
            if (body !is ByteReadChannel) return@intercept
            val request = context.request
            val response = context.response
            if (response.headers.contains(TOTAL) || request.url.parameters.contains(PAGE)) {
                val fullBody = body.readRemaining().readText()
                val currentPage = context.request.url.parameters[PAGE]?.toInt() ?: 1
                val total = response.headers[TOTAL]?.toInt() ?: 0
                val perPage = response.headers[PER_PAGE]?.toInt() ?: 0
                val nextPage = if (total > (currentPage * perPage)) currentPage + 1 else null
                val previousPage = if (currentPage == 1) null else currentPage - 1
                val newBody = buildString {
                    append(",")
                    append("\"total\":${total},")
                    append("\"perPage\":${perPage},")
                    append("\"nextPage\":${nextPage},")
                    append("\"previousPage\":${previousPage}")
                    append("}")
                }
                val finalBody = if (fullBody.startsWith(ARRAY_START)) {
                    "{\"items\":$fullBody$newBody"
                } else {
                    fullBody.dropLast(1) + newBody
                }
                proceedWith(HttpResponseContainer(info, ByteReadChannel(finalBody)))
            } else {
                proceed()
            }
        }
    }
}
