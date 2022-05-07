package kraken.plugin.api;

import java.security.SecureRandom;
import java.util.Random;

/**
 * All plugins must extend this base type.
 */
public abstract class Plugin {

    /**
     * A random instance that is seeded with information about the running account.
     */
    private final Random accountSpecificRandom = new Random(Kraken.getStaticRngSeed(3));

    /**
     * A random instance that is secure.
     */
    private final Random secureRandom = new SecureRandom();

    /**
     * Called when the plugin is loaded into the client.
     *
     * @return If the plugin will run or not.
     */
    public boolean onLoaded(PluginContext pluginContext) {
        return true;
    }

    /**
     * Called when this plugin is enabled in the plugin list.
     */
    public void onEnabled() {

    }

    /**
     * Called when this plugin is disabled in the plugin list.
     */
    public void onDisabled() {

    }

    /**
     * Called when the client is ticking, and it's time for us
     * to loop again. The client will wait for the amount of milliseconds
     * you return before calling this function again.
     *
     * @return The amount of time to wait before invoking this function again.
     */
    public int onLoop() {
        return 60000;
    }

    /**
     * Called when the plugin's window is being painted.
     */
    public void onPaint() {

    }

    /**
     * Called when the client's 3d overlay is being painted.
     */
    public void onPaintOverlay() {

    }

    /**
     * Called when a connection variable changes.
     */
    public void onConVarChanged(ConVar conv, int oldValue, int newValue) {

    }

    /**
     * Called when the visibility of a widget changes.
     */
    public void onWidgetVisibilityChanged(int id, boolean visible) {

    }

    /**
     * Called when the local player is changed. This is useful for initializing plugin data
     * about the local player.
     */
    public void onLocalPlayerChanged(Player self) {

    }

    /**
     * Called when an item in the inventory is changed.
     */
    public void onInventoryItemChanged(WidgetItem prev, WidgetItem next) {

    }

    /**
     * Called to determine if a break should be interrupted currently.
     */
    public boolean interruptBreak() {
        return false;
    }

    /**
     * @return A random instance that is seeded with information about the running account.
     */
    public Random getAccountSpecificRandom() {
        return accountSpecificRandom;
    }

    /**
     * @return A random instance that is secure.
     */
    public Random getSecureRandom() {
        return secureRandom;
    }
}
