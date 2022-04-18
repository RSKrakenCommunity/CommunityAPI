package com.rshub.api.entities.items

import com.rshub.api.actions.ActionHelper
import com.rshub.api.actions.GroundItemAction
import com.rshub.api.entities.WorldEntity
import com.rshub.api.pathing.LocalPathing
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import kraken.plugin.api.GroundItem
import kraken.plugin.api.Item
import kraken.plugin.api.Vector3
import kraken.plugin.api.Vector3i

class WorldItem(private val item: GroundItem) : WorldEntity, GameItem(Item(item.id, item.amount)) {
    override val globalPosition: Vector3i
        get() = item.globalPosition
    override val scenePosition: Vector3
        get() = item.scenePosition

    fun take() : Boolean {
        return interact(GroundItemAction.GROUND_ITEM3)
    }

    fun interact(option: String) : Boolean {
        val index = def.groundActions.indexOfFirst { it.equals(option, true) }
        for (value in GroundItemAction.values()) {
            if(value.actionIndex == index) {
                return interact(value)
            }
        }
        return false
    }

    fun interact(action: GroundItemAction) : Boolean {
        if(LocalPathing.isTileReachable(globalPosition.toTile())) {
            ActionHelper.menu(action, id, globalPosition.x, globalPosition.y)
            return true
        }
        return false
    }
}