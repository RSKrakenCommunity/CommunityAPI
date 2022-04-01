package com.rshub.api.widgets

import com.rshub.api.containers.Container
import com.rshub.api.entities.items.GameItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import kraken.plugin.api.Item
import kraken.plugin.api.WidgetItem
import kraken.plugin.api.Widgets

interface Widget {
    val widgetId: Int
    fun hasInventory(): Boolean
    fun addContainer(con: Container)
    fun getContainer(id: Int) : Container?

    suspend fun containerChanged(container: Container, prev: WidgetItem, next: WidgetItem)

    val events: MutableSharedFlow<WidgetEvent<*>>

    fun watchContainers(action: (Widget, Container, GameItem, GameItem) -> Unit)

    fun asWidgetItem(slot: Int, item: Item) : WidgetItem {
        return WidgetItem(item.id, item.amount, slot, widgetId)
    }

    fun isOpen() = Widgets.isOpen(widgetId)
    fun isClosed() = !isOpen()

    /**
     * Uses the ESC key to close the widget
     */
    fun exit()

    /**
     * Clicks the exit button on the widget
     */
    suspend fun close()

    companion object {
        fun containerChanged(container: Container, widget: Widget, slot: Int, prev: Item, next: Item) {
            runBlocking {
                widget.containerChanged(container, widget.asWidgetItem(slot, prev), widget.asWidgetItem(slot, next))
            }
        }
    }
}