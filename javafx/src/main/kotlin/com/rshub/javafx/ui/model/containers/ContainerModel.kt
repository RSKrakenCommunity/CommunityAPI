package com.rshub.javafx.ui.model.containers

import com.rshub.api.definitions.CacheHelper
import com.rshub.definitions.InventoryDefinition
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import kraken.plugin.api.ItemContainer
import tornadofx.ViewModel

class ContainerModel(container: ItemContainer) : ViewModel() {

    val container = bind { SimpleObjectProperty(this, "container", container) }
    val def: InventoryDefinition get() {
        val con = container.get()
        return CacheHelper.getInventory(con.id)
    }
    val size: Int get() = def.inventorySize

    val requireUpdate = bind { SimpleBooleanProperty(this, "require_update", false) }

}