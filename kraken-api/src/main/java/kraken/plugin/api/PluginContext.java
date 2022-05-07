package kraken.plugin.api;

public final class PluginContext {

    // internal values, attempting to use these will break the client

    private long internal1;

    /**
     * Do not make instances of this.
     */
    private PluginContext() {
    }

    /**
     * Retrieves the entry-point plugin object.
     *
     * @return The entry-point plugin object.
     */
    public native Plugin getEntry();

    /**
     * Retrieves the name of the plugin.
     *
     * @return The name of the plugin.
     */
    public native byte[] getNameBinary();

    /**
     * Retrieves the name of the plugin.
     *
     * @return The name of the plugin.
     */
    public String getName() {
        return new String(getNameBinary());
    }

    /**
     * Sets the name of the plugin.
     *
     * @param name The new name to set.
     */
    public native void setName(String name);

    /**
     * Sets the SDK version that this plugin uses.
     *
     * @param version The SDK version that this plugin uses.
     */
    public native void setSdkVersion(int version);

    /**
     * Retrieves the persistent data for this plugin. This data will be saved to disk.
     *
     * @return The persistent data for this plugin.
     */
    public native byte[] getPersistentData();

    /**
     * Stores new persistent data for this plugin.
     *
     * @param data The new persistent data to store.
     */
    public native void setPersistentData(byte[] data);

    /**
     * Determines whether or not this plugin is enabled.
     *
     * @return Whether or not this plugin is enabled.
     */
    public native boolean isEnabled();

    /**
     * Changes whether or not this plugin is enabled.
     *
     * @param enabled Whether this plugin will be enabled or disabled.
     */
    public native void setEnabled(boolean enabled);
}
