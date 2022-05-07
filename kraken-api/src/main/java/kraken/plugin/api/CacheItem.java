package kraken.plugin.api;

import java.nio.charset.StandardCharsets;

/**
 * An item configuration from the cache.
 */
public final class CacheItem extends AsyncData {

    private int id = 0;
    private long address = 0;
    private byte[] binaryName = Kraken.BAD_DATA_STRING.getBytes(StandardCharsets.US_ASCII);
    private byte[][] binaryOptionNames = new byte[0][];
    private byte[][] binaryGroundOptionNames = new byte[0][];

    CacheItem(int id) {
        this.id = id;
    }

    /**
     * @return The id of the item.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the address of this cache descriptor in memory.
     * This will only be valid in developer builds.
     *
     * @return The address of this cache descriptor in memory.
     */
    public long getAddress() {
        return address;
    }

    /**
     * @return The name of the item.
     */
    public String getName() {
        return new String(binaryName);
    }

    /**
     * @return The name of options when right clicking this item.
     */
    public String[] getOptionNames() {
        String[] options = new String[binaryOptionNames.length];
        for (int i = 0; i < binaryOptionNames.length; i++) {
            options[i] = binaryOptionNames[i] == null ? "" : new String(binaryOptionNames[i]);
        }
        return options;
    }

    /**
     * @return The name of options when right clicking this item on the ground.
     */
    public String[] getGroundOptionNames() {
        String[] options = new String[binaryGroundOptionNames.length];
        for (int i = 0; i < binaryGroundOptionNames.length; i++) {
            options[i] = binaryGroundOptionNames[i] == null ? "" : new String(binaryGroundOptionNames[i]);
        }
        return options;
    }

    /**
     * Determines if this item is loaded.
     *
     * @return If this item is loaded.
     */
    @Override
    public boolean isLoaded() {
        return super.isLoaded() && binaryName != null && binaryOptionNames != null;
    }
}
