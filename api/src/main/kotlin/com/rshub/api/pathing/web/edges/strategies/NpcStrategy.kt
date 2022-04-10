package com.rshub.api.pathing.web.edges.strategies

import com.rshub.api.actions.NpcAction
import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.edges.EdgeStrategy
import com.rshub.api.world.WorldHelper
import com.rshub.definitions.maps.WorldTile
import kotlinx.serialization.Serializable
import kraken.plugin.api.Client
import kraken.plugin.api.Players

@Serializable
class NpcStrategy(val npcId: Int, val location: WorldTile, val action: NpcAction) : EdgeStrategy {
    override fun traverse(edge: Edge): Boolean {
        val npc = WorldHelper.closestNpc { it.id == npcId } ?: return false
        return npc.interact(action, false)
    }

    override fun reached(edge: Edge): Boolean {
        val player = Players.self()?.globalPosition ?: return false
        return !Client.isLoading() && player.x == location.x && player.y == location.y && location.z == player.z
    }

    override fun modifyCost(cost: Int): Int {
        return 1
    }

    override fun modifyTimeout(timeout: Long): Long {
        return timeout + 5000
    }

    override fun blocked(edge: Edge): Boolean {
        val npc = WorldHelper.closestNpc { it.id == npcId } ?: return false
        return LocalPathing.isNpcReachable(npc)
    }
}