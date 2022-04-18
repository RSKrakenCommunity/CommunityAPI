package com.rshub.api.containers

import com.rshub.api.widgets.Widget
import kotlinx.coroutines.Job
import kraken.plugin.api.Filter
import kraken.plugin.api.Item
import kraken.plugin.api.WidgetItem

interface Container : Iterable<Item> {
    val containerId: Int
    val items: Array<Item>
    fun getItems(filter: Filter<in Item> = Filter { true }): List<Item>
    fun getItems(widget: Widget, filter: Filter<WidgetItem> = Filter { true }): List<WidgetItem>
    fun addListener(listener: (Int, Item, Item) -> Unit): Job
    fun slotOf(item: Item): Int
    fun fireChangeEvent(event: ContainerChangeEvent)
    operator fun get(slot: Int): Item
}