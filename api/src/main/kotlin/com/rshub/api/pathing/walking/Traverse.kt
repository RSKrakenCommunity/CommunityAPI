package com.rshub.api.pathing.walking

import com.rshub.api.coroutines.delay
import com.rshub.api.coroutines.delayUntil
import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.strategy.FixedTileStrategy
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.expand
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import kotlinx.coroutines.runBlocking
import kraken.plugin.api.Move
import kraken.plugin.api.Players

object Traverse {

    @JvmStatic
    fun move(tile: WorldTile) = runBlocking { moveTo(tile) }
    @JvmStatic
    fun walk(dest: WorldTile) = runBlocking { walkTo(dest) }

    suspend fun moveTo(tile: WorldTile): Boolean {
        val player = Players.self() ?: return false
        val isReachable =
            LocalPathing.getLocalStepsTo(player.globalPosition.toTile(), 1, FixedTileStrategy(tile), false) > -1
        if (isReachable) {
            Move.to(tile)
            val timeout = player.globalPosition.distance(tile).toLong()
            delayUntil((timeout * 650L)) { tile.expand(8).contains(Players.self()) }
            return true
        }
        return false
    }


    suspend fun walkTo(dest: WorldTile): Boolean {
        val ctx = TraversalContext(dest)
        var failure = false
        if(ctx.path.isEmpty()){
            return false
        }
        while (ctx.path.isNotEmpty() && !failure) {
            val player = ctx.player
            if (player == null) {
                failure = true
                continue
            }
            val node = ctx.path.poll() ?: continue
            if (node.traverse()) {
                val steps = LocalPathing.getLocalStepsTo(player.globalPosition.toTile(), 1, FixedTileStrategy(node.vertex.tile), false)
                val timeout = if(steps < 5000) {
                    5000
                } else steps * 650L
                if (delayUntil(timeout) { node.edge.reached() }) {
                    ctx.lastNode = node
                }
            } else {
                failure = true
                continue
            }
            delay(600)
        }
        if (!failure && ctx.path.isEmpty()) {
            val dist = ctx.player?.globalPosition?.distance(dest) ?: return false
            if (dist <= 63) {
                return moveTo(dest)
            }
        }
        return !failure
    }

}