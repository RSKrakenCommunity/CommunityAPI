package com.rshub.api.pathing.walking

import com.rshub.api.pathing.WalkHelper
import com.rshub.api.pathing.web.Graph.Companion.toWeb
import com.rshub.api.pathing.web.edges.strategies.NpcStrategy
import com.rshub.api.pathing.web.edges.strategies.ObjectStrategy
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import kraken.plugin.api.Debug
import kraken.plugin.api.Player
import kraken.plugin.api.Players
import java.util.*

class TraversalContext(val dest: WorldTile) {

    var lastNode: TraversalNode? = null
    val pathWalked: Queue<TraversalNode> = LinkedList()
    val player: Player? get() = Players.self()
    var path: Queue<TraversalNode> = LinkedList()
        private set

    init {
        generatePath()
    }

    private fun generatePath() {
        val plr = player ?: return
        val graph = WalkHelper.getGraph()
        val pos = plr.globalPosition.toTile()
        val start = graph.getAllVertices().minByOrNull { it.tile.distance(pos) }
        val end = graph.getAllVertices().minByOrNull { it.tile.distance(dest) }
        if (start != null && end != null) {
            if (end.tile.distance(dest) >= 63) {
                println("Dest not mapped.")
                return
            }
            val (path, _) = graph.toWeb().findPath(start, end)
            if(path.isNotEmpty()) {
                this.path = LinkedList(path)
            }
        }
    }

}