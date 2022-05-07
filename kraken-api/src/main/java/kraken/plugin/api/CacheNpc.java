package kraken.plugin.api;

import java.nio.charset.StandardCharsets;

/**
 * An NPC configuration from the cache.
 */
public final class CacheNpc extends AsyncData {

    private int id = 0;
    private long address = 0;
    private byte[] binaryName = Kraken.BAD_DATA_STRING.getBytes(StandardCharsets.US_ASCII);
    private byte[][] binaryOptionNames = new byte[0][];

    private int varbitId = -1;
    private int[] transformIds = new int[0];

    CacheNpc(int id) {
        this.id = id;
    }

    /**
     * @return The id of the NPC.
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
     * @return The name of the NPC.
     */
    public String getName() {
        return new String(binaryName);
    }

    /**
     * @return The name of options when right clicking this NPC.
     */
    public String[] getOptionNames() {
        String[] options = new String[binaryOptionNames.length];
        for (int i = 0; i < binaryOptionNames.length; i++) {
            options[i] = binaryOptionNames[i] == null ? "" : new String(binaryOptionNames[i]);
        }
        return options;
    }

    /**
     * Retrieves the id to use for varbit configuration on this NPC.
     *
     * @return The id to use for varbit configuration.
     */
    public int getVarbitId() {
        return varbitId;
    }

    /**
     * Retrieves the NPC ids that this NPC can transform into.
     *
     * @return The transform ids for this NPC.
     */
    public int[] getTransformIds() {
        return transformIds;
    }

    /**
     * Determines if this NPC is valid.
     *
     * @return If this NPC is valid.
     */
    public boolean isValid() {
        return super.isLoaded() && binaryName != null && binaryOptionNames != null;
    }
}
