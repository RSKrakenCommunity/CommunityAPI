package com.rshub.filesystem.test

import com.rshub.api.pathing.web.Graph
import com.rshub.api.pathing.web.edges.strategies.EdgeTileStrategy
import com.rshub.api.pathing.web.nodes.GraphVertex
import com.rshub.definitions.maps.WorldTile
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test

class GraphSerializationTest {

    @Test
    fun `serialize graph`() {
        val graph = Graph()
        val v1 = GraphVertex(WorldTile(0, 0, 0))
        val v2 = GraphVertex(WorldTile(10, 10, 0))
        val v3 = GraphVertex(WorldTile(30, 30, 0))

        graph.addVertex(v1)
        graph.addVertex(v2)
        graph.addVertex(v3)

        graph.addArc(v1 to v2, EdgeTileStrategy())
        graph.addArc(v2 to v3, EdgeTileStrategy())

        val json = Json {
            prettyPrint = true
        }

        val encoded = json.encodeToString(graph)

        println(encoded)

        val g1 = json.decodeFromString<Graph>(encoded)

        println(json.encodeToString(g1))

    }

}