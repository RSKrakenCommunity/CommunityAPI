package com.rshub.api.pathing.walking

import com.rshub.api.pathing.WalkHelper
import com.rshub.api.pathing.web.Graph.Companion.toWeb
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import kraken.plugin.api.Player
import kraken.plugin.api.Players
import java.util.*

class TraversalContext(val dest: WorldTile) {

    var lastNode: TraversalNode? = null
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
        val possibleStarts = graph.getAllVertices().filter { it.tile.distance(pos) < 63 }
        val start = possibleStarts.minByOrNull { it.tile.distance(pos) } ?: return
        val end = graph.getAllVertices().minByOrNull { it.tile.distance(dest) }!!
        if (end.tile.distance(dest) >= 63) {
            return
        }
        val web = graph.toWeb()
        val (route, _) = web.findPath(start, end)
        if(route.isNotEmpty()) {
            path = LinkedList(route)
        }
    }

}