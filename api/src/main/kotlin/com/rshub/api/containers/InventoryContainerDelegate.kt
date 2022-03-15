package com.rshub.api.containers

import kotlin.reflect.KProperty

typealias Widget = Int

class InventoryContainerDelegate(private val container: InventoryContainer) {
    operator fun getValue(any: Any?, prop: KProperty<*>) : InventoryContainer {
        return container
    }
    companion object {
        fun inv(id: Int, widgetHash: Int) = InventoryContainerDelegate(InventoryContainer(id, widgetHash))
        fun Widget.withInventory(id: Int) = InventoryContainerDelegate(InventoryContainer(id, this))
    }
}