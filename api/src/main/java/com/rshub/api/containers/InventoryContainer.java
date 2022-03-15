package com.rshub.api.containers;

import kraken.plugin.api.Item;
import kraken.plugin.api.ItemContainer;
import kraken.plugin.api.ItemContainers;
import kraken.plugin.api.WidgetItem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class InventoryContainer implements Iterable<WidgetItem> {
    
    private final int containerId;
    private final int widgetHash;
    private final List<WidgetItem> items;

    public InventoryContainer(int containerId, int widgetHash) {
        this.containerId = containerId;
        this.widgetHash = widgetHash;
        this.items = new ArrayList<>();
        this.loadContainer();
    }

    public int getContainerId() {
        return containerId;
    }

    public int getWidgetHash() {
        return widgetHash;
    }

    public List<WidgetItem> getItems() {
        return items;
    }

    private void loadContainer() {
        ItemContainer ic = ItemContainers.byId(containerId);
        if(ic == null) {
            return;
        }
        for (int i = 0; i < ic.getItems().length; i++) {
            Item item = ic.getItems()[i];
            if(item != null) {
                items.add(new WidgetItem(item.getId(), item.getAmount(), i, widgetHash));
            }
        }
    }

    public static InventoryContainer getInventory(int id, int widgetHash) {
        return new InventoryContainer(id, widgetHash);
    }

    @NotNull
    @Override
    public Iterator<WidgetItem> iterator() {
        return items.iterator();
    }

    public Stream<WidgetItem> stream() {
        return items.stream();
    }
}
