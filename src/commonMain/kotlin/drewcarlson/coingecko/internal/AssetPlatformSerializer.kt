package drewcarlson.coingecko.internal

import drewcarlson.coingecko.models.AssetPlatform
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure

@OptIn(ExperimentalSerializationApi::class)
internal object AssetPlatformSerializer : KSerializer<AssetPlatform> {
    private val nullStringSerializer = String.serializer().nullable
    private val nullLongSerializer = Long.serializer().nullable

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("drewcarlson.coingecko.models.AssetPlatform") {
            element<String?>("id")
            element<Long?>("chain_identifier")
            element<String>("name")
            element<String>("shortname")
        }

    override fun deserialize(decoder: Decoder): AssetPlatform {
        return decoder.decodeStructure(descriptor) {
            var id: String? = null
            var chainIdentifier: Long? = null
            var name: String? = null
            var shortname: String? = null
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> id = decodeNullableSerializableElement(descriptor, 0, nullStringSerializer)
                    1 -> chainIdentifier = decodeNullableSerializableElement(descriptor, 1, nullLongSerializer)
                    2 -> name = decodeStringElement(descriptor, 2)
                    3 -> shortname = decodeStringElement(descriptor, 3)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected element index: $index")
                }
            }

            val actualId = id.orEmpty().ifEmpty { checkNotNull(name).lowercase().replace(' ', '-') }
            AssetPlatform(actualId, chainIdentifier, checkNotNull(name), checkNotNull(shortname))
        }
    }

    override fun serialize(encoder: Encoder, value: AssetPlatform) {
        encoder.beginStructure(descriptor)
            .apply {
                encodeStringElement(descriptor, 0, value.id)
                encodeNullableSerializableElement(descriptor, 1, nullLongSerializer, value.chainIdentifier)
                encodeStringElement(descriptor, 2, value.name)
                encodeStringElement(descriptor, 3, value.shortname)
            }
            .endStructure(descriptor)
    }
}
