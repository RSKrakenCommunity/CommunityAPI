package com.rshub.api.player

import com.rshub.api.containers.Container
import com.rshub.api.containers.InventoryHelper
import com.rshub.api.coroutines.delayUntil
import com.rshub.api.entities.items.ContainerItem
import com.rshub.api.entities.items.ContainerItem.Companion.toContainerItem
import com.rshub.api.entities.items.GameItem
import com.rshub.api.input.KrakenInputHelper
import com.rshub.api.widgets.Widget
import com.rshub.api.widgets.WidgetEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import kraken.plugin.api.WidgetItem

object PlayerInventory : Widget {
    override val widgetId: Int = 1473
    override val containerChild: Int = 7
    private val container = InventoryHelper.getInventory(93)
    private val containerChildID = 7

    @JvmStatic
    fun count(filter: (ContainerItem) -> Boolean) : Int {
        return getItems(filter).count()
    }

    @JvmStatic
    fun first(filter: (ContainerItem) -> Boolean) : ContainerItem? {
        return getItems().firstOrNull(filter)
    }

    @JvmStatic
    fun getItems(filter : (ContainerItem) -> Boolean = { true }) : List<ContainerItem> {
        return container.getItems(this)
            .map { it.toContainerItem(container) }
            .filter(filter)
    }

    override fun hasInventory(): Boolean {
        return true
    }

    override fun addContainer(con: Container) {}

    override fun getContainer(id: Int): Container? {
        return container
    }

    override suspend fun containerChanged(container: Container, prev: WidgetItem, next: WidgetItem) {}

    override val events: MutableSharedFlow<WidgetEvent<*>> = MutableSharedFlow(extraBufferCapacity = 100)

    override fun watchContainers(action: (Widget, Container, GameItem, GameItem) -> Unit) {}

    override fun exit() {
        runBlocking { close() }
    }

    override suspend fun close() {
        if (isOpen()) {
            KrakenInputHelper.typeCharLiteral('B')
            delayUntil { !isOpen() }
        }
    }
}