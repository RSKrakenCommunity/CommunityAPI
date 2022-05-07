package kraken.plugin.api;

import java.util.LinkedList;
import java.util.List;

/**
 * Provides access to the local player's inventory.
 */
public final class Inventory {

    private static final int ITEM_CONTAINER_ID = 93;
    private static final int WIDGET_INTERACT_ID = 96534535;

    private Inventory() {
    }

    /**
     * Retrieves all items in the inventory.
     *
     * @return All items in the inventory.
     */
    public static WidgetItem[] getItems() {
        ItemContainer container = ItemContainers.byId(ITEM_CONTAINER_ID);
        if (container == null) {
            return new WidgetItem[0];
        }


        List<WidgetItem> list = new LinkedList<>();
        Item[] containerItems = container.getItems();
        for (int i = 0; i < containerItems.length; i++) {
            Item item = containerItems[i];
            if (item.getId() != -1) {
                list.add(new WidgetItem(item.getId(), item.getAmount(), i, WIDGET_INTERACT_ID));
            }
        }
        return list.toArray(new WidgetItem[0]);
    }

    /**
     * Counts the number of items that pass through the provided filter.
     *
     * @return The number of items that passed through the filter.
     */
    public static int count(Filter<WidgetItem> filter) {
        int count = 0;
        for (WidgetItem item : getItems()) {
            if (filter.accept(item)) {
                count += item.getAmount();
            }
        }
        return count;
    }

    /**
     * Finds the first widget item that passes the provided filter.
     *
     * @param filter The filter that items must pass through in order to be accepted.
     * @return The first item that passed the filter.
     */
    public static WidgetItem first(Filter<WidgetItem> filter) {
        for (WidgetItem item : getItems()) {
            if (filter.accept(item)) {
                return item;
            }
        }

        return null;
    }

    /**
     * Determines if the inventory is full.
     *
     * @return If the inventory is full.
     */
    public static boolean isFull() {
        return getItems().length == 28;
    }

    /**
     * Determines if the inventory is empty.
     *
     * @return If the inventory is empty.
     */
    public static boolean isEmpty() {
        return getItems().length == 0;
    }

    /**
     * Counts the number of free slots available in the inventory.
     *
     * @return The number of free slots available in the inventory.
     */
    public static int countFreeSlots() {
        return 28 - getItems().length;
    }

    /**
     * Determines if the inventory contains an item.
     *
     * @return If the inventory contains an item that passes through the provided filter.
     */
    public static boolean contains(Filter<WidgetItem> filter) {
        return count(filter) > 0;
    }

    /**
     * Iterates over each of the elements in the inventory.
     *
     * @param cb The callback to invoke with each element.
     */
    public static void forEach(Action1<WidgetItem> cb) {
        for (WidgetItem item : getItems()) {
            if (item != null) {
                cb.call(item);
            }
        }
    }
}
