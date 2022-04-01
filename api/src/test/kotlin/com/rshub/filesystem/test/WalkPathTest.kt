package com.rshub.filesystem.test

import com.rshub.api.pathing.WalkHelper
import com.rshub.api.pathing.walking.TraversalNode
import com.rshub.api.pathing.web.Graph.Companion.toWeb
import com.rshub.api.pathing.web.nodes.GraphVertex
import com.rshub.definitions.maps.WorldTile
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.collections.ArrayDeque

class WalkPathTest {

    fun `web walk test`() {
        WalkHelper.loadWeb()
        val graph = WalkHelper.getGraph()
        val plrTile = WorldTile(3212, 3373, 0)
        val destTile = WorldTile(3169, 3455, 0)
        val start = graph.getAllVertices().firstOrNull { it.tile.distance(plrTile) <= 63 }
        val end = graph.getAllVertices().minByOrNull { it.tile.distance(destTile) }

        if (start != null && end != null) {
            if (end.tile.distance(destTile) >= 63) {
                println("Dest not mapped.")
                return
            }


            val (path, dist) = graph.toWeb().findPath(start, end)

            println("Distance needed: $dist")

            val p = LinkedList(path)

            val c = p.poll()

            println(c.vertex.tile)
            println("---------------------------------")
            println(c.edge.from.tile)
            println(c.edge.to.tile)

        }
    }

}