package com.rshub.api.entities

import com.rshub.api.entities.items.WorldItem
import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.strategy.EntityStrategy
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import kraken.plugin.api.GroundItems
import kraken.plugin.api.Players
import java.util.*

class GroundItemManager {

    fun all(filter: WorldItem.() -> Boolean) = GroundItems.all()
        .filterNotNull()
        .map { WorldItem(it) }
        .filter(filter)

    fun closest(filter: WorldItem.() -> Boolean): WorldItem? {
        val player = Players.self()
        val distanceMap: MutableMap<Int, WorldItem> = TreeMap()
        val groundItems = all(filter)
        for (wo in groundItems) {
            if(wo.globalPosition.z != player.globalPosition.z)
                continue
            val tile = player.globalPosition.toTile()
            val distance = LocalPathing.getLocalStepsTo(tile, 1, EntityStrategy(wo), false)
            if (distance != -1) distanceMap[distance] = wo
        }
        if (distanceMap.isEmpty()) return null
        val sortedKeys = ArrayList(distanceMap.keys)
        sortedKeys.sort()
        return distanceMap[sortedKeys[0]]
    }

}