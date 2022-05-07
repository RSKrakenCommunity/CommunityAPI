package kraken.plugin.api;

/**
 * An item container, holds real information about items.
 */
public final class ItemContainer {

    /**
     * The id of this container.
     */
    private int id;

    /**
     * All items stored in this container.
     */
    private Item[] items;

    private ItemContainer() {
    }

    /**
     * @return The id of this container.
     */
    public int getId() {
        return id;
    }

    /**
     * @return All items stored in this container.
     */
    public Item[] getItems() {
        return items;
    }
}
