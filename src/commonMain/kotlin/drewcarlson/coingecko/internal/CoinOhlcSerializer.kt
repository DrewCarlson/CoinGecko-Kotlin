package drewcarlson.coingecko.internal

import drewcarlson.coingecko.models.coins.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*

object CoinOhlcSerializer : KSerializer<CoinOhlc> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("CoinOhlc")

    override fun serialize(encoder: Encoder, value: CoinOhlc) =
        error("CoinOhlc serialization not implemented")

    override fun deserialize(decoder: Decoder): CoinOhlc {
        val array = JsonArray.serializer().deserialize(decoder)
        return CoinOhlc(
            time = array[0].jsonPrimitive.long,
            open = array[1].jsonPrimitive.double,
            high = array[2].jsonPrimitive.double,
            low = array[3].jsonPrimitive.double,
            close = array[4].jsonPrimitive.double,
        )
    }
}
