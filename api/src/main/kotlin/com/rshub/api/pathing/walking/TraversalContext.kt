package com.rshub.api.pathing.walking

import com.rshub.api.coroutines.delayRandom
import com.rshub.api.coroutines.delayUntil
import com.rshub.api.definitions.CacheHelper
import com.rshub.api.lodestone.Lodestones
import com.rshub.api.pathing.WalkHelper
import com.rshub.api.pathing.teleport.Teleport
import com.rshub.api.pathing.web.Graph.Companion.toWeb
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.expand
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import kotlinx.coroutines.delay
import kraken.plugin.api.Player
import kraken.plugin.api.Players
import kraken.plugin.api.Widgets
import java.util.*

class TraversalContext(val dest: WorldTile) {

    val pathWalked: Queue<TraversalNode> = LinkedList()
    val player: Player? get() = Players.self()
    private val isQuickTeleport get() = CacheHelper.getVarbitValue(28622) == 1

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

    suspend fun traverse() : LinkedList<TraversalNode> {
        val player = Players.self()
        val plrDist = player.globalPosition.distance(dest)
        val lodestone = Lodestones.LODESTONES.filter { it.isAvailable() }.minByOrNull { it.dest.distance(dest) }
        if(lodestone != null) {
            val lodeDist = lodestone.dest.distance(dest)
            if (lodeDist < plrDist) {
                if(lodestone.teleport()) {
                    if(delayUntil(60000) { !Widgets.isOpen(1921) && lodestone.dest.expand(8).contains(Players.self()) }) {
                        if (!isQuickTeleport) {
                            delayUntil { Players.self()?.animationId == 0 }
                            delayUntil { Players.self()?.animationId == 16386 }
                            delayUntil { Players.self()?.animationId == 0 }
                        } else {
                            delayUntil(delay = 100) { Players.self()?.animationId == 0 }
                            delayUntil(delay = 100) { Players.self()?.animationId == 8941 }
                            delayUntil(delay = 100) { Players.self()?.animationId == 0 }
                        }
                        delay(1000)
                    }
                }
            }
        }
        return start(player.globalPosition.toTile())
    }
}