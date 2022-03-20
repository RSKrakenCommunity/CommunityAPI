package com.rshub.api.services.impl

import com.rshub.api.containers.ContainerChangeEvent
import com.rshub.api.services.GameStateHelper
import com.rshub.api.services.GameStateService
import kraken.plugin.api.ItemContainers

class ContainerStateService : GameStateService {
    override suspend fun stateChanged() {
        for (con in GameStateHelper.ITEM_CONTAINERS) {
            if (con != null && ItemContainers.isAvailable(con.containerId)) {
                val ic = ItemContainers.byId(con.containerId)
                val items = con.items
                for ((slot, item) in ic.items.withIndex()) {
                    if(items[slot] != item) {
                        val prev = items[slot]
                        con.fireChangeEvent(ContainerChangeEvent(slot, prev, item))
                        items[slot] = item
                    }
                }
            }
        }
    }
}