package com.rshub.api.pathing.web.edges.strategies

import com.rshub.api.actions.ObjectAction
import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.edges.EdgeStrategy
import com.rshub.api.world.WorldHelper
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import kotlinx.serialization.Serializable

@Serializable
class DoorStrategy(
    val objectId: Int,
    val action: ObjectAction,
    val objectTile: WorldTile
) : EdgeStrategy {
    override fun traverse(edge: Edge): Boolean {
        val door = WorldHelper.closestObjectIgnoreClip { it.id == objectId && it.globalPosition.toTile() == objectTile }
        return door?.interact(action) ?: false
    }

    override fun reached(edge: Edge): Boolean {
        val door = WorldHelper.closestObjectIgnoreClip { it.id == objectId && it.globalPosition.toTile() == objectTile }
        return door == null
    }

    override fun modifyCost(cost: Int): Int {
        return cost
    }

    override fun blocked(edge: Edge): Boolean {
        return false
    }
}