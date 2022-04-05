package com.rshub.filesystem.test

import com.rshub.api.actions.ObjectAction
import com.rshub.api.pathing.WalkHelper
import com.rshub.api.pathing.web.Graph
import com.rshub.api.pathing.web.edges.EdgeStrategy
import com.rshub.api.pathing.web.edges.strategies.DoorStrategy
import com.rshub.api.pathing.web.edges.strategies.EdgeTileStrategy
import com.rshub.api.pathing.web.edges.strategies.NpcStrategy
import com.rshub.api.pathing.web.edges.strategies.ObjectStrategy
import com.rshub.api.pathing.web.nodes.GraphVertex
import com.rshub.api.skills.Skill
import com.rshub.definitions.maps.WorldTile
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
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
        graph.addArc(v2 to v3, ObjectStrategy(
            1,
            2,
            3,
            4,
            ObjectAction.OBJECT1,
            Skill.ATTACK,
            25
        ))

        val json = Json {
            prettyPrint = true
            serializersModule = SerializersModule {
                polymorphic(EdgeStrategy::class) {
                    subclass(EdgeTileStrategy::class)
                    subclass(ObjectStrategy::class)
                    subclass(NpcStrategy::class)
                    subclass(DoorStrategy::class)
                }
            }
        }

        val encoded = json.encodeToString(graph)

        println(encoded)

        val g1 = json.decodeFromString<Graph>(encoded)

        println(json.encodeToString(g1))

    }

    @Test
    fun `actual graph serialization`() {
        WalkHelper.loadWeb()
        var graph = WalkHelper.getGraph()

        graph.getAllEdges().forEach {
            val strat = it.strategy
            if(strat is ObjectStrategy) {
                val id = strat.objectId
                val x = strat.objectX
                val y = strat.objectY
                val z = strat.objectZ
                println("Object($id, $x, $y, $z)")
            }
        }

        WalkHelper.saveWeb()

        WalkHelper.loadWeb()

        graph = WalkHelper.getGraph()

        graph.getAllEdges().forEach {
            val strat = it.strategy
            if(strat is ObjectStrategy) {
                val id = strat.objectId
                val x = strat.objectX
                val y = strat.objectY
                val z = strat.objectZ
                println("Object($id, $x, $y, $z)")
            }
        }

    }

}