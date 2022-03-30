package com.rshub.api.pathing.json

import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.edges.EdgeStrategy
import com.rshub.api.pathing.web.edges.strategies.EdgeTileStrategy
import com.rshub.api.pathing.web.nodes.GraphVertex
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

object EdgeSerializer : KSerializer<Edge> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("edge") {
        element<GraphVertex>("from")
        element<GraphVertex>("to")
        element<EdgeStrategy>("strategy")
    }

    override fun serialize(encoder: Encoder, value: Edge) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, GraphVertex.serializer(), value.from)
            encodeSerializableElement(descriptor, 1, GraphVertex.serializer(), value.to)
            encodeStringElement(descriptor, 2, value.strategy::class.java.simpleName)
        }
    }

    override fun deserialize(decoder: Decoder): Edge {
        decoder.decodeStructure(descriptor) {
            val from = decodeSerializableElement(descriptor, 0, GraphVertex.serializer())
            val to = decodeSerializableElement(descriptor, 1, GraphVertex.serializer())
            val strategyName = decodeStringElement(descriptor,2)
            val strategy = when(strategyName) {
               "EdgeTileStrategy" -> EdgeTileStrategy()
                else -> error("Unknown strategy")
            }
            return Edge(from, to, strategy)
        }
    }
}