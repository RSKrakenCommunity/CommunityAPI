package com.rshub.filesystem.test

import com.rshub.api.pathing.WalkHelper
import com.rshub.api.pathing.walking.TraversalContext
import com.rshub.api.pathing.web.Graph.Companion.toWeb
import com.rshub.api.pathing.web.edges.strategies.NpcStrategy
import com.rshub.api.pathing.web.edges.strategies.ObjectStrategy
import com.rshub.definitions.maps.WorldTile
import org.junit.jupiter.api.Test
import java.util.*

class WalkPathTest {

    @Test
    fun `web walk test`() {
        WalkHelper.loadWeb()
        val graph = WalkHelper.getGraph()
        val plrTile = WorldTile(3235, 3220, 0)
        val destTile = WorldTile(3297, 3184, 0)
        val start = graph.getAllVertices().minByOrNull { it.tile.distance(plrTile) }
        val end = graph.getAllVertices().minByOrNull { it.tile.distance(destTile) }

        if (start != null && end != null) {
            if (end.tile.distance(destTile) >= 63) {
                println("Dest not mapped.")
                return
            }
            val ctx = TraversalContext(destTile)

            val path = ctx.start(plrTile)

            val p = LinkedList(path)

            while(p.isNotEmpty()) {
                val n = p.poll() ?: continue
                println("----------------------------------")
                println(n.vertex.tile)
                val strat = n.edge.strategy
                when (strat) {
                    is ObjectStrategy -> {
                        println("Object(${strat.objectId}, ${strat.objectX}, ${strat.objectY})")
                    }
                    is NpcStrategy -> {
                        println("Npc(${strat.npcId}, ${strat.action.name}) - ${strat.location}")
                    }
                    else -> {
                        println(n.edge.strategy::class.simpleName)
                    }
                }
            }
        }
    }

}