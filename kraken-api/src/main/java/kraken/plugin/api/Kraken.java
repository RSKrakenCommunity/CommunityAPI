package kraken.plugin.api;

import java.nio.charset.StandardCharsets;

/**
 * Provides access to kraken itself.
 */
public final class Kraken {

    // these will be randomized, do not use
    public static final int PROTECTION_ID_DECRYPT_INT = 8851721;
    public static final int PROTECTION_ID_DECRYPT_BINARY = 1876334;
    // if any strings are equal to this, they were unavailable or something of the sort.
    public static final String BAD_DATA_STRING = "N/A";

    private Kraken() {
    }

    /**
     * Loads a new plugin into the client.
     *
     * @param entry The entry-point of the plugin to load.
     */
    public static native void loadNewPlugin(Class<?> entry);

    /**
     * Retrieves the path to the plugin directory.
     *
     * @return The path to the plugin directory.
     */
    public static native byte[] getPluginDirBinary();

    /**
     * Retrieves the path to the plugin directory.
     *
     * @return The path to the plugin directory.
     */
    public static String getPluginDir() {
        return new String(getPluginDirBinary(), StandardCharsets.US_ASCII);
    }

    /**
     * Performs a call into the protection subsystem. Documentation will not be provided for this system.
     * This will be used internally, and in bundled plugins such as the SDN to provide increased protection
     * for paid products which cannot be provided in pure Java.
     * <p>
     * Calls into this function will not work unless the calling jar is signed.
     *
     * @param id   The protection id.
     * @param args The protection arguments.
     * @return The protection result.
     */
    public static native Object protection(int id, Object[] args);

    /**
     * Brings the game window into focus.
     */
    public static native void focusGameWindow();

    /**
     * Toggles rendering on or off.
     *
     * @param enabled If rendering will be on or off.
     */
    public static native void toggleRendering(boolean enabled);

    /**
     * Changes the delay between rendering each frame of the game.
     *
     * @param renderDelay The new render delay.
     */
    public static native void setRenderDelay(int renderDelay);

    /**
     * Toggles the auto login feature.
     *
     * @param enabled If auto login should be enabled or disabled.
     */
    public static native void toggleAutoLogin(boolean enabled);

    /**
     * Forces Kraken to take a break right now.
     *
     * @param ms The number of milliseconds to take a break for.
     */
    public static native void takeBreak(long ms);

    /**
     * Retrieves the number of times the client has been restarted in this session (due to crashes, etc.)
     * This can be useful for determining if you should initialize persistent data in your plugin.
     */
    public static native int getRestartCount();

    /**
     * Determines if this is a new session. Useful for initializing variables in plugins related to runtime, experience, etc.
     */
    public static boolean isNewSession() {
        return getRestartCount() == 0;
    }

    /**
     * Retrieves the context of each plugin loaded into the client currently.
     *
     * @return Each plugin loaded into the client currently.
     */
    public static native PluginContext[] getAllPlugins();

    /**
     * Finds a plugin with a specific name.
     *
     * @param name The name to search for.
     * @return The found plugin, or NULL if one does not exist.
     */
    public static PluginContext getPluginByName(String name) {
        for (PluginContext context : getAllPlugins()) {
            if (context.getName().equals(name)) {
                return context;
            }
        }
        return null;
    }

    /**
     * Retrieves the email of the currently running account.
     */
    public static native byte[] getEmailBinary();

    /**
     * Retrieves the email of the currently running account.
     */
    public static String getEmail() {
        return new String(getEmailBinary());
    }

    /**
     * Retrieves the method to use for randomizing world selection on login. NULL = disabled.
     */
    public static native WorldType getRandomizeWorldLoginType();

    /**
     * Changes the method to use for randomizing world selection on login. NULL = disabled.
     */
    public static native void setRandomizeWorldLoginType(WorldType type);

    /**
     * Retrieves the world being selected on login.
     */
    public static native int getLoginWorld();

    /**
     * Changes the world being selected on login.
     */
    public static native void setLoginWorld(int world);

    /**
     * Retrieves a seed that can be used for RNG that will be the same every time this specific account runs.
     *
     * @param mutations The number of mutations to apply to the seed. Can be used to retrieve different seeds for each plugin.
     * @return The seed to use for RNG.
     */
    public static native int getStaticRngSeed(int mutations);

    /**
     * Sets the status that will be displayed on the Kraken loader. This should only be used by primary plugins
     * that will take control of the client.
     *
     * @param status The status to display in   the loader.
     */
    public static native void setStatus(String status);


    public static native int getIntroConfiguration();
}
