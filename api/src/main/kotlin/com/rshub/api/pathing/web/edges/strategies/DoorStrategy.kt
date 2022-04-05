package com.rshub.api.pathing.web.edges.strategies

import com.rshub.api.actions.ObjectAction
import com.rshub.api.map.Region
import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.edges.EdgeStrategy
import com.rshub.api.world.WorldHelper
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import kotlinx.serialization.Serializable
import kraken.plugin.api.Debug

@Serializable
class DoorStrategy(
    val objectId: Int,
    val action: ObjectAction,
    val objectTile: WorldTile
) : EdgeStrategy {

    override fun traverse(edge: Edge): Boolean {
        Debug.log("Door $objectId - $objectTile")
        val obj = WorldHelper.closestObjectIgnoreClip {
            val pos = it.globalPosition
            it.id == objectId && pos.x == objectTile.x && pos.y == objectTile.y && pos.z == objectTile.z
        }
        if(obj != null) {
            val success = obj.interact(action, false)
            Debug.log("Opening door $success")
            return success
        }
        return true
    }

    override fun reached(edge: Edge): Boolean {
        val obj = WorldHelper.closestObjectIgnoreClip {
            it.id == objectId && it.globalPosition.x == objectTile.x && it.globalPosition.y == objectTile.y && it.globalPosition.z == objectTile.z
        }

        Region.validateObjCoords(objectId, obj.globalPosition.toTile())

        Debug.log("Door open: ${obj == null}")
        return obj == null
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