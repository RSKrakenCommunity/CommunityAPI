package kraken.plugin.api;

import java.util.Objects;

/**
 * An item that is stored in a widget.
 */
public class WidgetItem extends Item {

    private int slot;
    private int widgetId = -1;

    public WidgetItem() {
    }

    public WidgetItem(int id, int amount, int slot) {
        super(id, amount);
        this.slot = slot;
    }

    public WidgetItem(int id, int amount, int slot, int widgetId) {
        super(id, amount);
        this.slot = slot;
        this.widgetId = widgetId;
    }

    /**
     * Retrieves the item container slot that this item is in within
     * the widget.
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Changes the slot this item is in. Should only be used internally.
     */
    public void setSlot(int slot) {
        this.slot = slot;
    }

    /**
     * Retrieves the interact id of the widget this item is bound to.
     */
    public int getWidgetId() {
        return widgetId;
    }

    /**
     * Changes the interact id of the widget this item is bound to. Should only be used internally.
     */
    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    /**
     * Interacts with this widget item. This may not be available on all widgets.
     *
     * @param option The option to interact with.
     */
    public boolean interact(int option) {
        if (widgetId == -1) {
            return false;
        }

        Actions.menu(Actions.MENU_EXECUTE_WIDGET, option, slot, widgetId, 0);
        return true;
    }

    @Override
    public String toString() {
        return "WidgetItem{" +
                "id=" + getId() +
                ", amount= " + getAmount() +
                ", slot=" + slot +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WidgetItem that = (WidgetItem) o;
        return slot == that.slot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), slot);
    }
}
