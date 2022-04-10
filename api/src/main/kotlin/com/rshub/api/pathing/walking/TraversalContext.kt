package com.rshub.api.pathing.walking

import com.rshub.api.pathing.WalkHelper
import com.rshub.api.pathing.web.Graph.Companion.toWeb
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import kraken.plugin.api.Player
import kraken.plugin.api.Players
import java.util.*

class TraversalContext(val dest: WorldTile) {

    val pathWalked: Queue<TraversalNode> = LinkedList()
    val player: Player? get() = Players.self()

    fun start(startTile: WorldTile) : LinkedList<TraversalNode> {
        val graph = WalkHelper.getGraph()
        val start = graph.getAllVertices().minByOrNull { it.tile.distance(startTile) }
        val end = graph.getAllVertices().minByOrNull { it.tile.distance(dest) }
        if (start != null && end != null) {
            if (end.tile.distance(dest) >= 63) {
                println("Dest not mapped.")
                return LinkedList()
            }
            val (path, _) = graph.toWeb().findPath(start, end)
            return LinkedList(path)
        }
        return LinkedList()
    }
}