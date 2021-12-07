package drewcarlson.coingecko.internal

import kotlinx.serialization.json.*
import kotlinx.serialization.serializer


internal object NullValueOmittingMapSerializer : JsonTransformingSerializer<Map<String, Double>>(serializer()) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return if (element is JsonObject && element.any { (_, value) -> value == JsonNull }) {
            JsonObject(element.filterValues { it !is JsonNull })
        } else {
            super.transformDeserialize(element)
        }
    }
}