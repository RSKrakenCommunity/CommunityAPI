package kraken.plugin.api;

/**
 * Provides simplified access to the deposit bank widgets.
 */
public final class Deposit {

    private static final int WIDGET_ID = 11;
    private static final int WIDGET_INTERACT_ID = 720915;
    private static final int WIDGET_DEPOSIT_INVENTORY_ID = 720901;
    private static final int WIDGET_DEPOSIT_EQUIPMENT_ID = 720904;
    private static final int WIDGET_DEPOSIT_BOB_ID = 720907;
    private static final int WIDGET_DEPOSIT_MONEY_ID = 720910;

    private Deposit() {
    }

    /**
     * Determines if the deposit widget is open.
     *
     * @return If the deposit widget is open.
     */
    public static boolean isOpen() {
        return Widgets.isOpen(11);
    }

    /**
     * Deposits all inventory items into the bank.
     */
    public static void depositInventory() {
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, WIDGET_DEPOSIT_INVENTORY_ID, 0);
    }

    /**
     * Deposits all equipment into the bank.
     */
    public static void depositEquipment() {
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, WIDGET_DEPOSIT_EQUIPMENT_ID, 0);
    }

    /**
     * Deposits all items from the active BoB into the bank.
     */
    public static void depositBob() {
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, WIDGET_DEPOSIT_BOB_ID, 0);
    }

    /**
     * Deposits all money from the pouch into the bank.
     */
    public static void depositMoney() {
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, WIDGET_DEPOSIT_MONEY_ID, 0);
    }

    /**
     * Retrieves all items in the deposit widget.
     *
     * @return All items in the deposit widget.
     */
    public static WidgetItem[] getItems() {
        WidgetItem[] items = Inventory.getItems();
        for (WidgetItem item : items) {
            item.setWidgetId(WIDGET_INTERACT_ID);
        }
        return items;
    }
}
