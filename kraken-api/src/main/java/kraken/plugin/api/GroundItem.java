package kraken.plugin.api;

import java.util.Objects;

import static kraken.plugin.api.Actions.*;

/**
 * An item on the ground.
 */
public final class GroundItem extends Entity {

    private static final int[] OPTION_NAME_MAP = {
            MENU_EXECUTE_GROUND_ITEM1,
            MENU_EXECUTE_GROUND_ITEM2,
            MENU_EXECUTE_GROUND_ITEM3,
            MENU_EXECUTE_GROUND_ITEM4,
            MENU_EXECUTE_GROUND_ITEM5,
            MENU_EXECUTE_GROUND_ITEM6,
    };

    // internal values, attempting to use these will break the client

    private int internal10;

    private int id;
    private int amount;

    /**
     * Do not make instances of this.
     */
    private GroundItem() {
    }

    /**
     * Retrieves the id of the item stack.
     *
     * @return The id of the item stack.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the amount of item in the item stack.
     *
     * @return The amount of item in the item stack.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Retrieves the name of this item.
     *
     * @return The name of this item.
     */
    public String getName() {
        CacheItem item = Cache.getItem(id, true);
        if (item == null) {
            return Kraken.BAD_DATA_STRING;
        }

        return item.getName();
    }

    /**
     * Retrieves the names of options when right clicking this ground item.
     *
     * @return The names of options when right clicking this ground item.
     */
    public String[] getOptionNames() {
        CacheItem item = Cache.getItem(id, true);
        if (item == null) {
            return new String[0];
        }

        return item.getGroundOptionNames();
    }

    /**
     * Interacts with this ground item.
     *
     * @param type The type of action to use on the ground item.
     */
    public void interact(int type) {
        Actions.entity(this, type);
    }

    /**
     * Interacts with this ground item.
     */
    public boolean interact(String option) {
        String[] options = getOptionNames();
        int m = Math.min(OPTION_NAME_MAP.length, options.length);
        for (int i = 0; i < m; i++) {
            if (option.equalsIgnoreCase(options[i])) {
                interact(OPTION_NAME_MAP[i]);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GroundItem that = (GroundItem) o;
        return internal10 == that.internal10;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), internal10);
    }
}
