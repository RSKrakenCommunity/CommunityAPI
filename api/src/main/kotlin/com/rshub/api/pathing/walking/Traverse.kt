package com.rshub.api.pathing.walking

import com.rshub.api.coroutines.delay
import com.rshub.api.coroutines.delayUntil
import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.strategy.FixedTileStrategy
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.expand
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kraken.plugin.api.Debug
import kraken.plugin.api.Move
import kraken.plugin.api.Players

object Traverse {

    @JvmStatic
    fun move(tile: WorldTile) = runBlocking { moveTo(tile) }

    @JvmStatic
    fun walk(dest: WorldTile) = runBlocking { walkTo(dest) }

    suspend fun moveTo(tile: WorldTile): Boolean {
        val player = Players.self() ?: return false
        val steps =
            LocalPathing.getLocalStepsTo(player.globalPosition.toTile(), 1, FixedTileStrategy(tile), false)
        val isReachable = steps > -1
        if (isReachable) {
            Move.to(tile)
            val timeout = steps * 800L
            return delayUntil(if(timeout < 5000) 5000 else timeout) {
                val pos = Players.self()?.globalPosition ?: return@delayUntil true
                pos.x == tile.x && pos.y == tile.y
            }
        }
        return false
    }


    suspend fun walkTo(dest: WorldTile): Boolean {
        val ctx = TraversalContext(dest)
        val path = ctx.traverse()
        if(walkToDest(ctx, dest)) {
            return true
        } else {
            var failure = false
            if (path.isEmpty()) {
                Debug.log("No path found on walkTo")
                return false
            }
            while (path.isNotEmpty() && !failure) {
                val player = ctx.player
                if (player == null) {
                    Debug.log("Player null on loop start.")
                    failure = true
                    continue
                }
                val node = path.poll() ?: continue
                if (node.traverse()) {
                    val steps = LocalPathing.getLocalStepsTo(
                        player.globalPosition.toTile(),
                        1,
                        FixedTileStrategy(node.edge.from.tile),
                        false
                    )
                    val waitFor = (steps * 650L)
                    val timeout = if (waitFor < 5000) {
                        5000
                    } else waitFor
                    if(delayUntil(node.edge.strategy.modifyTimeout(timeout)) { node.edge.reached() }) {
                        ctx.pathWalked.offer(node)
                    }
                } else {
                    Debug.log("Traverse failed: ${node.edge.strategy::class.java.simpleName}")
                    failure = true
                    continue
                }
                delay(600)
            }
            if (!failure && path.isEmpty()) {
                return walkToDest(ctx, dest)
            }
            return !failure
        }
    }

    private suspend fun walkToDest(ctx: TraversalContext, dest: WorldTile): Boolean {
        val player = ctx.player
        if (player == null) {
            Debug.log("Player is null on dest walk.")
            return false
        }
        val dist = player.globalPosition.distance(dest)
        if (dist <= 63) {
            return moveTo(dest)
        }
        return false
    }

}