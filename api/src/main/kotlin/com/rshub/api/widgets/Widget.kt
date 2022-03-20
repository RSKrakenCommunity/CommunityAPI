package com.rshub.api.widgets

import com.rshub.api.containers.Container
import kraken.plugin.api.Item
import kraken.plugin.api.WidgetItem

interface Widget {
    val widgetId: Int
    fun hasInventory(): Boolean
    fun addContainer(con: Container)
    fun containerChanged(container: Container, prev: WidgetItem, next: WidgetItem)

    fun asWidgetItem(slot: Int, item: Item) : WidgetItem {
        return WidgetItem(item.id, item.amount, slot, widgetId)
    }
}