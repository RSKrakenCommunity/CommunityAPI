package com.rshub.api.pathing.web.edges.strategies

import com.rshub.api.actions.NpcAction
import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.edges.EdgeStrategy
import com.rshub.api.world.WorldHelper
import com.rshub.definitions.maps.WorldTile.Companion.expand
import kotlinx.serialization.Serializable
import kraken.plugin.api.Players

@Serializable
class NpcStrategy(val npcId: Int, val action: NpcAction) : EdgeStrategy {
    override fun traverse(edge: Edge): Boolean {
        val npc = WorldHelper.closestNpc { it.id == npcId } ?: return false
        return npc.interact(action)
    }

    override fun reached(edge: Edge): Boolean {
        val npc = WorldHelper.closestNpc { it.id == npcId } ?: return false
        return npc.getTile().expand(8).contains(Players.self())
    }

    override fun modifyCost(cost: Int): Int {
        return cost
    }

    override fun blocked(edge: Edge): Boolean {
        return LocalPathing.isReachable(edge.from.tile)
    }
}