package com.rshub.services

import com.rshub.api.definitions.CacheHelper
import com.rshub.api.services.GameStateService
import com.rshub.javafx.ui.model.RSItemModel
import com.rshub.javafx.ui.model.containers.ContainerFilterModel
import com.rshub.javafx.ui.model.containers.ContainerModel
import kraken.plugin.api.ItemContainer
import kraken.plugin.api.ItemContainers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tornadofx.runLater

class ContainerUpdateService : GameStateService, KoinComponent {

    private val model: ContainerFilterModel by inject()

    override suspend fun stateChanged() {
        val ids = ItemContainers.all().map { it.id }
        val toRemove = mutableListOf<Int>()
        val toAdd = mutableListOf<ItemContainer>()
        for ((key, value) in model.containers) {
            if (key !in ids) {
                toRemove.add(key)
            }

        }
        for (id in ids) {
            val con = ItemContainers.byId(id)
            if (con != null) {
                toAdd.add(con)
            }
        }
        runLater {
            toRemove.forEach { model.containers.remove(it) }
            toAdd.forEach { model.containers.putIfAbsent(it.id, ContainerModel(it)) }
            if (model.selectedContainer.get() != null) {
                val sel = model.selectedContainer.get()
                val selCon = sel.container.get()
                val items = sel.container.get().items
                val con = ItemContainers.byId(selCon.id) ?: return@runLater
                for (index in items.indices) {
                    val backing = items[index]
                    val item = con.items[index]
                    if(backing.id != item.id || backing.amount != item.amount) {
                        sel.container.set(con)
                        model.items.setAll(con.items.map { RSItemModel(CacheHelper.getItem(it.id), it.amount) })
                        break
                    }
                }
            }
        }
    }
}