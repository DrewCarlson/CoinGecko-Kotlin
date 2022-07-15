package drewcarlson.coingecko.internal

import kotlinx.serialization.json.*
import kotlinx.serialization.serializer

internal object NullValueOmittingListSerializer : JsonTransformingSerializer<List<String>>(serializer()) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return if (element is JsonArray && element.any { it == JsonNull }) {
            JsonArray(element.filter { it != JsonNull })
        } else {
            super.transformDeserialize(element)
        }
    }
}
