package com.rshub.api.entities

import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.strategy.ObjectStrategy
import com.rshub.definitions.maps.WorldObject
import kraken.plugin.api.SceneObject
import kraken.plugin.api.SceneObjects
import java.util.*

class WorldObjectManager {
    fun all(filter: (WorldObject) -> Boolean) = SceneObjects.all()
        .filterNotNull()
        .filterNot { it.hidden() }
        .map { it.toWorldObject() }
        .filter(filter)

    fun closest(filter: WorldObject.() -> Boolean) : WorldObject? {
        val distanceMap: MutableMap<Int, WorldObject> = TreeMap()
        val objects: List<WorldObject> = all(filter)
        for (wo in objects) {
            val distance = LocalPathing.getLocalStepsTo(wo.tile, 1, ObjectStrategy(wo), false)
            if (distance != -1) distanceMap[distance] = wo
        }
        if (distanceMap.isEmpty()) return null
        val sortedKeys = ArrayList(distanceMap.keys)
        sortedKeys.sort()
        return distanceMap[sortedKeys[0]]
    }

    companion object {
        fun SceneObject.toWorldObject() : WorldObject {
            val pos = globalPosition
            return WorldObject(id, pos.x, pos.y, pos.z)
        }
    }
}