package com.rshub.api.entities.items

import com.rshub.api.actions.ActionHelper
import com.rshub.api.actions.MenuAction
import com.rshub.api.containers.Container
import kraken.plugin.api.WidgetItem

class ContainerItem(val container: Container, widgetItem: WidgetItem) : GameItem(widgetItem) {
    val slot: Int by lazy { widgetItem.slot }
    val widgetId: Int by lazy { widgetItem.widgetId }

    fun interact(option: Int): Boolean {
        ActionHelper.menu(MenuAction.WIDGET, option, slot, widgetId)
        return true
    }

    companion object {
        fun WidgetItem.toContainerItem(container: Container) = ContainerItem(container, this)
    }
}