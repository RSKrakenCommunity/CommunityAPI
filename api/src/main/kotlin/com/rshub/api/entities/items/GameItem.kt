package com.rshub.api.entities.items

import com.rshub.api.definitions.CacheHelper
import com.rshub.definitions.ItemDefinition
import kraken.plugin.api.Item

abstract class GameItem(private val item: Item) {

    val id get() = item.id
    val amount get() = item.amount

    val def: ItemDefinition by lazy { CacheHelper.getItem(id) }

    val name get() = def.name
    val category get() = def.category
    val equipmentSlot get() = def.equipmentSlot
    val options get() = def.inventoryActions
    val groundOptions get() = def.groundActions
    val equipmentOptions get() = def.wornActions
    val grandExchangeBuyLimit get() = def.geBuyLimit
    val isMembers get() = def.members
    val shopPrice get() = def.price
    val isStackable get() = def.stackable
    val isSearchable get() = def.searchable
    val questIds get() = def.questIds
}