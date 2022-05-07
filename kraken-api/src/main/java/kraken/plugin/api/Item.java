package kraken.plugin.api;

import java.util.Objects;

/**
 * A generic item.
 */
public class Item {

    private int id;
    private int amount;

    public Item() {
    }

    public Item(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public Item(int id) {
        this(id, 1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
     * Retrieves the names of all options on this item.
     *
     * @return The names of all options on this item.
     */
    public String[] getOptionNames() {
        CacheItem item = Cache.getItem(id, true);
        if (item == null) {
            return new String[0];
        }

        return item.getOptionNames();
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                amount == item.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }
}
