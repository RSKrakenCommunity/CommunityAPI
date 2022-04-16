package com.rshub.json.serialization

import com.rshub.definitions.maps.WorldTile
import com.rshub.javafx.ui.model.walking.LocationModel
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import kraken.plugin.api.Client
import kraken.plugin.api.Widgets

object LocationModelSerializer : KSerializer<LocationModel> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("location") {
        element<String>("name")
        element<WorldTile>("tile")
        element<Boolean>("isBank")
    }

    override fun deserialize(decoder: Decoder): LocationModel {
        decoder.decodeStructure(descriptor) {
            var name = ""
            var tile = WorldTile(0, 0, 0)
            var isBank = false
            while(true) {
                when(val index = decodeElementIndex(descriptor)) {
                    0 -> name = decodeStringElement(descriptor, index)
                    1 -> tile = decodeSerializableElement(descriptor, index, WorldTile.serializer())
                    2 -> isBank = decodeBooleanElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                }
            }
            return LocationModel(name, tile, isBank)
        }
    }

    override fun serialize(encoder: Encoder, value: LocationModel) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.name.get())
            encodeSerializableElement(descriptor, 1, WorldTile.serializer(), value.tile.get())
            encodeBooleanElement(descriptor, 2, value.bank.get())
        }
    }
}