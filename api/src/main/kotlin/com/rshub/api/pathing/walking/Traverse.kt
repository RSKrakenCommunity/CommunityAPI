package com.rshub.api.pathing.walking

import com.rshub.api.coroutines.delay
import com.rshub.api.coroutines.delayUntil
import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.strategy.FixedTileStrategy
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.expand
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import kotlinx.coroutines.runBlocking
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
            return delayUntil(timeout) { tile.expand(8).contains(Players.self()) }
        }
        return false
    }


    suspend fun walkTo(dest: WorldTile): Boolean {
        val ctx = TraversalContext(dest)
        if (!walkToDest(ctx, dest)) {
            var failure = false
            if (ctx.path.isEmpty()) {
                Debug.log("No path found on walkTo")
                return false
            }
            while (ctx.path.isNotEmpty() && !failure) {
                val player = ctx.player
                if (player == null) {
                    Debug.log("Player null on loop start.")
                    failure = true
                    continue
                }
                val node = ctx.path.poll() ?: continue
                if (node.traverse()) {
                    val steps = LocalPathing.getLocalStepsTo(
                        player.globalPosition.toTile(),
                        1,
                        FixedTileStrategy(node.vertex.tile),
                        false
                    )
                    val timeout = if (steps < 5000) {
                        5000
                    } else steps * 800L
                    if (delayUntil(timeout) { node.edge.reached() }) {
                        ctx.pathWalked.offer(node)
                    }
                } else {
                    Debug.log("Traverse failed: ${node.edge.strategy::class.java.simpleName}")
                    failure = true
                    continue
                }
                delay(600)
            }
            if (!failure && ctx.path.isEmpty()) {
                return walkToDest(ctx, dest)
            }
            return !failure
        }
        return false
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