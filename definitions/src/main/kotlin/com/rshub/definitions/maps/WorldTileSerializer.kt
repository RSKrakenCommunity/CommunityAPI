package com.rshub.definitions.maps

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*

object WorldTileSerializer : KSerializer<WorldTile> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("tile") {
        element<Int>("x")
        element<Int>("y")
        element<Int>("z")
    }

    override fun serialize(encoder: Encoder, value: WorldTile) {
        encoder.encodeStructure(descriptor) {
            encodeIntElement(descriptor, 0, value.x)
            encodeIntElement(descriptor, 1, value.y)
            encodeIntElement(descriptor, 2, value.z)
        }
    }

    override fun deserialize(decoder: Decoder): WorldTile {
        decoder.decodeStructure(descriptor) {
            var x = -1
            var y = -1
            var z = -1
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeIntElement(descriptor, index)
                    1 -> y = decodeIntElement(descriptor, index)
                    2 -> z = decodeIntElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                }
            }
            return WorldTile(x, y, z)
        }
    }
}