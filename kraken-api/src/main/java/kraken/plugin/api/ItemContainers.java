package kraken.plugin.api;

/**
 * Provides access to item containers.
 */
public final class ItemContainers {

    private ItemContainers() {
    }

    /**
     * Retrieves all item continers.
     *
     * @return All item continers.
     */
    public static native ItemContainer[] all();

    /**
     * Retrieves an item container by its id.
     *
     * @param id The id of the item container.
     * @return The item container.
     */
    public static native ItemContainer byId(int id);

    /**
     * Determines if an item container is available or not.
     *
     * @param id The id of the item container to find.
     * @return If an item container was found with the provided id.
     */
    public static boolean isAvailable(int id) {
        return byId(id) != null;
    }
}
