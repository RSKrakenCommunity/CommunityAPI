package com.rshub.api.containers

import com.rshub.api.definitions.CacheHelper
import com.rshub.api.widgets.Widget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kraken.plugin.api.Filter
import kraken.plugin.api.Item
import kraken.plugin.api.WidgetItem
import java.util.stream.Stream
import java.util.stream.StreamSupport

class Inventory(override val containerId: Int) : Container {
    private val changeFlow = MutableSharedFlow<ContainerChangeEvent>(extraBufferCapacity = 10)
    private val def get() = CacheHelper.getInventory(containerId)
    override val items = Array(def.inventorySize) { Item(-1) }

    override fun getItems(filter: Filter<in Item>): List<Item> {
        val items = mutableListOf<Item>()
        for (item in this.items) {
            if (filter.accept(item)) {
                items.add(item)
            }
        }
        return items
    }

    override fun getItems(widget: Widget, filter: Filter<WidgetItem>): List<WidgetItem> {
        val items = getItems()
        val list = mutableListOf<WidgetItem>()
        for ((slot, item) in items.withIndex()) {
            val widgetItem = WidgetItem(item.id, item.amount, slot, widget.widgetId)
            if (filter.accept(widgetItem)) {
                list.add(widgetItem)
            }
        }
        return list
    }

    override fun addListener(listener: (Int, Item, Item) -> Unit): Job {
        return changeFlow.onEach { listener.invoke(it.slot, it.prev, it.next) }
            .launchIn(CoroutineScope(Dispatchers.Default))
    }

    override fun slotOf(item: Item) : Int {
        for ((slot, i) in items.withIndex()) {
            if(i == item){
                return slot
            }
        }
        return -1
    }

    override fun fireChangeEvent(event: ContainerChangeEvent) {
        changeFlow.tryEmit(event)
    }

    override fun get(slot: Int): Item? {
        for ((index, item) in items.withIndex()) {
            if(index == slot) {
                return item
            }
        }
        return null
    }

    override fun iterator(): Iterator<Item> {
        return object : Iterator<Item> {
            private val items = getItems()
            private var index = 0
            override fun hasNext(): Boolean {
                return index < items.size
            }

            override fun next(): Item {
                return items[index++]
            }
        }
    }

    fun stream(parallel: Boolean = false): Stream<Item> {
        return StreamSupport.stream(spliterator(), parallel)
    }
}