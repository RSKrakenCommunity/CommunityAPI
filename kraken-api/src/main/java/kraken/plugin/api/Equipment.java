package kraken.plugin.api;

import java.util.LinkedList;
import java.util.List;

/**
 * Provides simplified access to the equipment widget.
 */
public final class Equipment {

    private static final int ITEM_CONTAINER_ID = 670;
    private static final int WIDGET_INTERACT_ID = 95944719;

    private Equipment() {
    }

    /**
     * Retrieves all items displayed in the equipment widget.
     *
     * @return All items displayed in the equipment widget.
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
}
