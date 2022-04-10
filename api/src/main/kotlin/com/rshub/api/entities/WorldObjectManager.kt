package com.rshub.api.entities

import com.rshub.api.entities.objects.WorldObject
import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.strategy.ObjectStrategy
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import kraken.plugin.api.Players
import kraken.plugin.api.SceneObjects
import java.util.*

class WorldObjectManager {
    fun all(filter: (WorldObject) -> Boolean) = SceneObjects.all()
        .filterNotNull()
        .map { WorldObject(it) }
        .filter(filter)

    fun at(x: Int, y: Int) = SceneObjects.at(x, y)?.map { WorldObject(it) } ?: emptyList()

    fun closestIgnoreClip(filter: WorldObject.() -> Boolean): WorldObject? {
        val player = Players.self() ?: return null
        return all(filter)
            .minByOrNull { it.globalPosition.distance(player.globalPosition) }
    }

    fun closest(filter: WorldObject.() -> Boolean): WorldObject? {
        val player = Players.self() ?: return null
        val distanceMap: MutableMap<Int, WorldObject> = TreeMap()
        val objects = all(filter)
        for (wo in objects) {
            if (wo.globalPosition.z != player.globalPosition.z)
                continue
            val distance = LocalPathing.getLocalStepsTo(player.globalPosition.toTile(), 1, ObjectStrategy(wo), true)
            if (distance != -1) distanceMap[distance] = wo
        }
        if (distanceMap.isEmpty()) return null
        val sortedKeys = ArrayList(distanceMap.keys)
        sortedKeys.sort()
        return distanceMap[sortedKeys[0]]
    }
}