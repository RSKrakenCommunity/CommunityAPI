package com.rshub.api.pathing.web.edges.strategies

import com.rshub.api.actions.ObjectAction
import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.edges.EdgeStrategy
import com.rshub.api.world.WorldHelper
import com.rshub.definitions.maps.WorldTile
import kotlinx.serialization.Serializable
import kraken.plugin.api.Debug

@Serializable
class DoorStrategy(
    val openDoorId: Int,
    val closedDoorId: Int,
    val openDoorTile: WorldTile,
    val closedDoorTile: WorldTile,
    val action: ObjectAction,
) : EdgeStrategy {

    override fun traverse(edge: Edge): Boolean {
        val open = WorldHelper.closestObjectIgnoreClip {
            val pos = it.globalPosition
            it.id == openDoorId && pos.x == openDoorTile.x && pos.y == openDoorTile.y && pos.z == openDoorTile.z
        }
        if(open == null) {
            Debug.log("Open door not found $openDoorId - $openDoorTile")
        }
        return open?.interact(action, false) ?: false
    }

    override fun reached(edge: Edge): Boolean {
        val closed = WorldHelper.closestObjectIgnoreClip {
            val pos = it.globalPosition
            it.id == closedDoorId && pos.x == closedDoorTile.x && pos.y == closedDoorTile.y && pos.z == closedDoorTile.z
        }
        if(closed == null) {
            Debug.log("No Opened Door Found $closedDoorId - $closedDoorTile")
        }
        return closed != null
    }

    override fun modifyCost(cost: Int): Int {
        return cost
    }

    override fun blocked(edge: Edge): Boolean {
        return false
    }

    override fun skipThreshold(): Int {
        return 1
    }
}