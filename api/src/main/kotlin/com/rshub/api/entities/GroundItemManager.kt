package com.rshub.api.entities

import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.strategy.EntityStrategy
import com.rshub.definitions.maps.WorldTile
import kraken.plugin.api.GroundItem
import kraken.plugin.api.GroundItems
import java.util.*

class GroundItemManager {

    fun all(filter: GroundItem.() -> Boolean) = GroundItems.all()
        .filterNotNull()
        .filter(filter)

    fun closest(filter: GroundItem.() -> Boolean) : GroundItem? {
        val distanceMap: MutableMap<Int, GroundItem> = TreeMap()
        val groundItems = all(filter)
        for (wo in groundItems) {
            val tile = WorldTile(wo.globalPosition.x, wo.globalPosition.y, wo.globalPosition.z)
            val distance = LocalPathing.getLocalStepsTo(tile, 1, EntityStrategy(wo), false)
            if (distance != -1) distanceMap[distance] = wo
        }
        if (distanceMap.isEmpty()) return null
        val sortedKeys = ArrayList(distanceMap.keys)
        sortedKeys.sort()
        return distanceMap[sortedKeys[0]]
    }

}