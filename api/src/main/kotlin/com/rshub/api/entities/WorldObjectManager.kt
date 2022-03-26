package com.rshub.api.entities

import com.rshub.api.entities.objects.WorldObject
import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.strategy.ObjectStrategy
import kraken.plugin.api.SceneObjects
import java.util.*

class WorldObjectManager {
    fun all(filter: (WorldObject) -> Boolean) = SceneObjects.all()
        .filterNotNull()
        .map { WorldObject(it) }
        .filterNot { it.hidden }
        .filter(filter)

    fun closest(filter: WorldObject.() -> Boolean) : WorldObject? {
        val distanceMap: MutableMap<Int, WorldObject> = TreeMap()
        val objects = all(filter)
        for (wo in objects) {
            val distance = LocalPathing.getLocalStepsTo(wo.getTile(), 1, ObjectStrategy(wo), false)
            if (distance != -1) distanceMap[distance] = wo
        }
        if (distanceMap.isEmpty()) return null
        val sortedKeys = ArrayList(distanceMap.keys)
        sortedKeys.sort()
        return distanceMap[sortedKeys[0]]
    }
}