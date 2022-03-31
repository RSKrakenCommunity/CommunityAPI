package com.rshub.json.serialization

import com.rshub.definitions.maps.WorldTile
import com.rshub.javafx.ui.model.walking.LocationModel
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*

object LocationModelSerializer : KSerializer<LocationModel> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("location") {
        element<String>("name")
        element<WorldTile>("tile")
    }

    override fun deserialize(decoder: Decoder): LocationModel {
        decoder.decodeStructure(descriptor) {
            var name = ""
            var tile = WorldTile(0, 0, 0)
            while(true) {
                when(val index = decodeElementIndex(descriptor)) {
                    0 -> name = decodeStringElement(descriptor, index)
                    1 -> tile = decodeSerializableElement(descriptor, index, WorldTile.serializer())
                    CompositeDecoder.DECODE_DONE -> break
                }
            }
            return LocationModel(name, tile)
        }
    }

    override fun serialize(encoder: Encoder, value: LocationModel) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.name.get())
            encodeSerializableElement(descriptor, 1, WorldTile.serializer(), value.tile.get())
        }
    }
}