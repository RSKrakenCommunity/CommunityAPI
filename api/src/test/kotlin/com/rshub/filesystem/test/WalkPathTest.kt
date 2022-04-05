package com.rshub.filesystem.test

import com.rshub.api.pathing.WalkHelper
import com.rshub.api.pathing.web.Graph.Companion.toWeb
import com.rshub.definitions.maps.WorldTile
import org.junit.jupiter.api.Test
import java.util.*

class WalkPathTest {

    fun `web walk test`() {
        WalkHelper.loadWeb()
        val graph = WalkHelper.getGraph()
        val plrTile = WorldTile(2967, 3402, 0)
        val destTile = WorldTile(2957, 3296, 0)
        val possibleStarts = graph.getAllVertices().filter { it.tile.distance(plrTile) < 63 }
        val start = possibleStarts.minByOrNull { it.tile.distance(plrTile) }
        val end = graph.getAllVertices().minByOrNull { it.tile.distance(destTile) }

        if (start != null && end != null) {
            if (end.tile.distance(destTile) >= 63) {
                println("Dest not mapped.")
                return
            }

            val (path, dist) = graph.toWeb().findPath(start, end)

            println("Distance needed: $dist")

            val p = LinkedList(path)

            while(p.isNotEmpty()) {
                val n = p.poll() ?: continue
                println(n.edge.strategy::class.simpleName)
            }
        }
    }

}