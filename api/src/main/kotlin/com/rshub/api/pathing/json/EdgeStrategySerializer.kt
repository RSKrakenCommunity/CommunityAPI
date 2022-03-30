package com.rshub.api.pathing.json

import com.rshub.api.pathing.web.edges.EdgeStrategy
import com.rshub.api.pathing.web.edges.strategies.EdgeTileStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object EdgeStrategySerializer : KSerializer<EdgeStrategy> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("strategy", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: EdgeStrategy) {
        encoder.encodeString(value::class.java.simpleName)
    }

    override fun deserialize(decoder: Decoder): EdgeStrategy {
        return when(decoder.decodeString()) {
            "EdgeTileStrategy" -> EdgeTileStrategy()
            else -> error("Unknown edge strategy")
        }
    }
}