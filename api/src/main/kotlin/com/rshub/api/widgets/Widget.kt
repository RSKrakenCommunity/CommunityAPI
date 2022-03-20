package com.rshub.api.widgets

import com.rshub.api.containers.Container
import kraken.plugin.api.Item
import kraken.plugin.api.WidgetItem
import kraken.plugin.api.Widgets

interface Widget {
    val widgetId: Int
    fun hasInventory(): Boolean
    fun addContainer(con: Container)
    fun getContainer(id: Int) : Container?
    fun containerChanged(container: Container, prev: WidgetItem, next: WidgetItem)

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
    fun close()
}