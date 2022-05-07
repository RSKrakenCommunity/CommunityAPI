package kraken.plugin.api;

import java.util.LinkedList;
import java.util.List;

/**
 * Provides access to the local player's bank.
 */
public final class Bank {

    private static final int ITEM_CONTAINER_ID = 95;
    private static final int WIDGET_INTERACT_ID = 33882303;
    private static final int WIDGET_INVENTORY_INTERACT_ID = 33882127;
    private static final int WIDGET_EMPTY_BACKPACK_INTERACT_ID = 33882151;
    private static final int WIDGET_EMPTY_EQUIPMENT_INTERACT_ID = 33882154;
    private static final int WIDGET_TOGGLE_NOTES_INTERACT_ID = 33882239;

    private Bank() {
    }

    /**
     * Determines if the bank widget is open.
     *
     * @return If the bank widget is open.
     */
    public static boolean isOpen() {
        return ItemContainers.isAvailable(ITEM_CONTAINER_ID);
    }

    /**
     * Retrieves all items in the bank.
     *
     * @return All items in the bank.
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
     * Counts the number of items that pass the filter.
     *
     * @return The number of items that pass the filter.
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
     * Determines if the bank is empty.
     *
     * @return If the bank is empty.
     */
    public static boolean isEmpty() {
        return getItems().length == 0;
    }

    /**
     * Determines if the bank contains an item.
     *
     * @return If the bank contains an item that passes the provided filter.
     */
    public static boolean contains(Filter<WidgetItem> filter) {
        return count(filter) > 0;
    }

    /**
     * Iterates over each of the elements in the bank.
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

    /**
     * Withdraws some items from the bank.
     *
     * @param filter The filter items must pass through in order to be withdrawn.
     * @param option The menu option to use for withdrawing.
     */
    public static void withdraw(Filter<WidgetItem> filter, int option) {
        forEach((item) -> {
            if (filter.accept(item)) {
                item.interact(option);
            }
        });
    }

    /**
     * Deposits items into the bank.
     *
     * @param filter The filter items must pass through in order to be deposited.
     * @param option The menu option to use for depositing.
     */
    public static void deposit(Filter<WidgetItem> filter, int option) {
        Inventory.forEach((item) -> {
            if (filter.accept(item)) {
                item.setWidgetId(WIDGET_INVENTORY_INTERACT_ID);
                item.interact(option);
            }
        });
    }

    /**
     * Deposits all items into the bank.
     */
    public static void depositAll() {
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, WIDGET_EMPTY_BACKPACK_INTERACT_ID, 0);
    }

    /**
     * Deposits all equipment into the bank.
     */
    public static void depositEquipment() {
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, WIDGET_EMPTY_EQUIPMENT_INTERACT_ID, 0);
    }

    /**
     * Determines if items are being withdrawn as notes or not.
     */
    public static boolean isWithdrawingNotes() {
        ConVar cv = Client.getConVarById(ConVar.ID_WITHDRAW_NOTE);
        if (cv == null) {
            return false;
        }

        return cv.getValueInt() == ConVar.CFG_WITHDRAW_NOTE_ACTIVE;
    }

    /**
     * Changes whether or not we are withdrawing items in noted form.
     */
    public static void setWithdrawingNotes(boolean notes) {
        if (isWithdrawingNotes() != notes) {
            Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, WIDGET_TOGGLE_NOTES_INTERACT_ID, 0);
        }
    }
}
