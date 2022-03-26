package com.rshub.api.entities.items

import com.rshub.api.entities.WorldEntity
import kraken.plugin.api.GroundItem
import kraken.plugin.api.Item
import kraken.plugin.api.Vector3
import kraken.plugin.api.Vector3i

class WorldItem(private val item: GroundItem) : WorldEntity, GameItem(Item(item.id, item.amount)) {
    override val globalPosition: Vector3i
        get() = item.globalPosition
    override val scenePosition: Vector3
        get() = item.scenePosition
}