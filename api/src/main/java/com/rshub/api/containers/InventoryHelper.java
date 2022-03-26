package com.rshub.api.containers;

import com.rshub.api.services.GameStateHelper;

public final class InventoryHelper {

    private InventoryHelper() {
    }

    public static Inventory getInventory(int invId) {
        Inventory inv = new Inventory(invId);
        GameStateHelper.ITEM_CONTAINERS.add(inv);
        return inv;
    }

    public static void removeInventory(int invId) {
        GameStateHelper.ITEM_CONTAINERS.removeIf(inv -> inv.getContainerId() == invId);
    }
}
