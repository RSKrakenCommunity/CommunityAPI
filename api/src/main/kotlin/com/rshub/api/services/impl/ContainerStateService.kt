package com.rshub.api.services.impl

import com.rshub.api.containers.ContainerChangeEvent
import com.rshub.api.services.GameStateHelper
import com.rshub.api.services.GameStateService
import kraken.plugin.api.Debug
import kraken.plugin.api.Item
import kraken.plugin.api.ItemContainers

class ContainerStateService : GameStateService {
    override suspend fun stateChanged() {
        for (con in GameStateHelper.ITEM_CONTAINERS) {
            if (con != null && ItemContainers.isAvailable(con.containerId)) {
                val ic = ItemContainers.byId(con.containerId)
                for ((slot, item) in ic.items.withIndex()) {
                    if(con[slot].id != item.id || con[slot].amount != item.amount) {
                        val prev = con[slot]
                        con.items[slot] = Item(item.id, item.amount)
                        con.fireChangeEvent(ContainerChangeEvent(slot, prev, item))
                    }
                }
            }
        }
    }
}