package kraken.plugin.api;

/**
 * A provider of entities.
 */
public final class Entities {

    private Entities() {
    }

    /**
     * Finds an entity by their server index.
     *
     * @param index The index to search for.
     * @return The found entity, or NULL if one was not found.
     */
    public static Entity byServerIndex(int index) {
        Player p = Players.byServerIndex(index);
        if (p != null) {
            return p;
        }

        return Npcs.byServerIndex(index);
    }

}
