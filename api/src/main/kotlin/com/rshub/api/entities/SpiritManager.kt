package com.rshub.api.entities

import com.rshub.api.definitions.CacheHelper
import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.strategy.EntityStrategy
import com.rshub.definitions.maps.WorldTile
import kraken.plugin.api.*
import java.util.*

class SpiritManager {

    fun all(filter: Spirit.() -> Boolean) = listOfNotNull(*Npcs.all(), *Players.all())
        .filter(filter)

    fun players(filter: Player.() -> Boolean) = Players.all().filterNotNull().filter(filter)
    fun npcs(filter: Npc.() -> Boolean) = Npcs.all().filterNotNull().filter(filter)

    fun closest(filter: Spirit.() -> Boolean): Spirit? {
        val distanceMap: MutableMap<Int, Spirit> = TreeMap()
        val spirits: List<Spirit> = all(filter)
        for (wo in spirits) {
            val tile = WorldTile(wo.globalPosition.x, wo.globalPosition.y, wo.globalPosition.z)
            val srcSize = if (wo is Npc) {
                val def = CacheHelper.getNpc(wo.id)
                def.size
            } else 1
            val distance = LocalPathing.getLocalStepsTo(tile, srcSize, EntityStrategy(wo, srcSize, 0), false)
            if (distance != -1) distanceMap[distance] = wo
        }
        if (distanceMap.isEmpty()) return null
        val sortedKeys = ArrayList(distanceMap.keys)
        sortedKeys.sort()
        return distanceMap[sortedKeys[0]]
    }

    fun closestNpc(filter: Npc.() -> Boolean): Npc? {
        val distanceMap: MutableMap<Int, Npc> = TreeMap()
        val spirits = npcs(filter)
        for (wo in spirits) {
            val tile = WorldTile(wo.globalPosition.x, wo.globalPosition.y, wo.globalPosition.z)
            val def = CacheHelper.getNpc(wo.id)
            val distance = LocalPathing.getLocalStepsTo(tile, def.size, EntityStrategy(wo), false)
            if (distance != -1) distanceMap[distance] = wo
        }
        if (distanceMap.isEmpty()) return null
        val sortedKeys = ArrayList(distanceMap.keys)
        sortedKeys.sort()
        return distanceMap[sortedKeys[0]]
    }

    fun closestPlayer(filter: Player.() -> Boolean): Player? {
        val distanceMap: MutableMap<Int, Player> = TreeMap()
        val spirits = players(filter)
        for (wo in spirits) {
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