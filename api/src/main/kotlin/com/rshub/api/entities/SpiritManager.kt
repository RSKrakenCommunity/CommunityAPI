package com.rshub.api.entities

import com.rshub.api.entities.spirits.WorldSpirit
import com.rshub.api.entities.spirits.npc.WorldNpc
import com.rshub.api.entities.spirits.player.WorldPlayer
import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.strategy.EntityStrategy
import com.rshub.definitions.maps.WorldTile
import kraken.plugin.api.*
import java.util.*

class SpiritManager {

    fun all(filter: WorldSpirit.() -> Boolean) =
        listOfNotNull(
            *Npcs.all().map { WorldNpc(it) }.toTypedArray(),
            *Players.all().map { WorldPlayer(it) }.toTypedArray()
        ).filter(filter)

    fun players(filter: WorldPlayer.() -> Boolean) = Players.all()
        .filterNotNull()
        .map { WorldPlayer(it) }
        .filter(filter)
    fun npcs(filter: WorldNpc.() -> Boolean) = Npcs.all()
        .filterNotNull()
        .map { WorldNpc(it) }
        .filter(filter)

    fun closest(filter: WorldSpirit.() -> Boolean): WorldSpirit? {
        val distanceMap: MutableMap<Int, WorldSpirit> = TreeMap()
        val spirits: List<WorldSpirit> = all(filter)
        for (wo in spirits) {
            val tile = WorldTile(wo.globalPosition.x, wo.globalPosition.y, wo.globalPosition.z)
            val srcSize = if (wo is WorldNpc) {
                wo.def.size
            } else 1
            val distance = LocalPathing.getLocalStepsTo(tile, srcSize, EntityStrategy(wo, srcSize, 0), false)
            if (distance != -1) distanceMap[distance] = wo
        }
        if (distanceMap.isEmpty()) return null
        val sortedKeys = ArrayList(distanceMap.keys)
        sortedKeys.sort()
        return distanceMap[sortedKeys[0]]
    }

    fun closestNpc(filter: WorldNpc.() -> Boolean): WorldNpc? {
        val distanceMap: MutableMap<Int, WorldNpc> = TreeMap()
        val player = Players.self() ?: return null
        val spirits = npcs(filter)
        if(spirits.isEmpty()) {
            Debug.log("No npcs found.")
            return null
        }
        val start = WorldTile(player.globalPosition.x, player.globalPosition.y, player.globalPosition.z)
        for (wo in spirits) {
            val distance = LocalPathing.getLocalStepsTo(start, wo.def.size, EntityStrategy(wo), false)
            if (distance != -1) {
                distanceMap[distance] = wo
            } else {
                Debug.log("Npc was not found.")
            }
        }
        if (distanceMap.isEmpty()) {
            return null
        }
        val sortedKeys = ArrayList(distanceMap.keys)
        sortedKeys.sort()
        return distanceMap[sortedKeys[0]]
    }

    fun closestPlayer(filter: WorldPlayer.() -> Boolean): WorldPlayer? {
        val distanceMap: MutableMap<Int, WorldPlayer> = TreeMap()
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