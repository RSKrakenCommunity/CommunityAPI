package kraken.plugin.api;

import java.util.LinkedList;
import java.util.List;

/**
 * Provides simplified access to the loot widget.
 */
public final class Loot {

    private static final int ITEM_CONTAINER_ID = 773;
    private static final int WIDGET_ID = 1622;
    private static final int WIDGET_INTERACT_ID = 106299402;
    private static final int WIDGET_CLOSE_INTERACT_ID = 106299400;
    private static final int WIDGET_LOOT_ALL_INTERACT_ID = 106299413;

    private Loot() {
    }

    /**
     * Opens the loot widget by clicking the nearest ground item.
     */
    public static boolean open() {
        if (isOpen()) {
            return true;
        }

        GroundItem item = GroundItems.closest((i) -> true);
        if (item == null) {
            return false;
        }

        item.interact(Actions.MENU_EXECUTE_GROUND_ITEM3);
        return true;
    }

    /**
     * Determines if the looting widget is open.
     *
     * @return If the looting widget is open.
     */
    public static boolean isOpen() {
        return Widgets.isOpen(WIDGET_ID) && ItemContainers.isAvailable(ITEM_CONTAINER_ID);
    }

    /**
     * Closes the loot widget if open.
     */
    public static void close() {
        if (!isOpen()) {
            return;
        }

        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, WIDGET_CLOSE_INTERACT_ID, 0);
    }

    /**
     * Retrieves all items displayed in the loot container.
     *
     * @return All items displayed in the loot container.
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
     * Counts the number of items in the loot widget that passed the provided filter.
     *
     * @param filter The filter to use for counting.
     * @return The number of items that passed the filter.
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
     * Finds the first item that passes the provided filter.
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
     * Determines if the loot widget contains any items that pass the provided filter.
     *
     * @param filter The filter.
     * @return If any items in the widget pass the provided filter.
     */
    public static boolean contains(Filter<WidgetItem> filter) {
        return first(filter) != null;
    }

    /**
     * Clicks the take all button in the loot widget.
     */
    public static void takeAll() {
        if (!isOpen()) {
            return;
        }

        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, WIDGET_LOOT_ALL_INTERACT_ID, 0);
    }
}
