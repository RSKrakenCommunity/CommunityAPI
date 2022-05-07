package kraken.plugin.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a more detailed way of managing plugins at the Java level.
 */
public abstract class ManagedPlugin<K, A extends ManagedPlugin.PluginAction> extends Plugin {

    /**
     * If this option is set, none of the plugin functions will be invoked while the cache is loading data.
     */
    public static final int OPTION_WAIT_CACHE_LOAD = (1 << 0);

    /**
     * If this option is set, the on loop function will be invoked unless the local player is valid.
     * The local player will also be stored in this class to ensure validity.
     */
    public static final int OPTION_LOOP_WAIT_LOCAL_PLAYER = (1 << 1);
    /**
     * A map of actions to execute.
     */
    private final Map<K, A> actionMap = new HashMap<>();
    /**
     * The cached instance of the local player from the last loop. Only valid if the OPTION_LOOP_WAIT_LOCAL_PLAYER option is set.
     */
    protected Player self;
    /**
     * All options to use for deciding plugin behavior.
     */
    private int options = 0;
    /**
     * The default amount of milliseconds to wait in onLoop when the managed version is not called.
     */
    private int defaultLoopDelay = 1000;
    /**
     * The current state.
     */
    private K activeState;

    /**
     * Adds an option to this plugin.
     */
    public void addOption(int option) {
        options |= option;
    }

    /**
     * Removes an option from this plugin.
     */
    public void removeOption(int option) {
        options &= ~option;
    }

    /**
     * Determines if this plugin has an option set.
     */
    public boolean hasOption(int option) {
        return (options & option) != 0;
    }

    /**
     * Changes the default amount of milliseconds to wait in onLoop when the managed version is not called.
     */
    public void setDefaultLoopDelay(int defaultLoopDelay) {
        this.defaultLoopDelay = defaultLoopDelay;
    }

    /**
     * Adds an action to the action map.
     */
    public void addAction(K key, A action) {
        actionMap.put(key, action);
    }

    /**
     * Changes the state.
     */
    public void changeState(K state) {
        if (activeState != null) {
            A action = actionMap.get(activeState);
            if (action != null) {
                action.onExit();
            }
        }

        activeState = state;
        A action = actionMap.get(activeState);
        if (action != null) {
            action.onEnter();
        }
    }

    /**
     * Determines if this plugin is busy waiting on the cache to finish loading.
     */
    private boolean waitingOnCache() {
        return hasOption(OPTION_WAIT_CACHE_LOAD) && Cache.loading();
    }

    /**
     * Determines if this plugin is busy waiting on anything to finish happening.
     */
    private boolean waiting() {
        return waitingOnCache();
    }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        return super.onLoaded(pluginContext);
    }

    protected int onLoopManaged() {
        if (activeState == null) {
            Debug.log("State is not set");
            return defaultLoopDelay;
        }

        A action = actionMap.get(activeState);
        if (action == null) {
            Debug.log("Plugin action does not exist state state'" + activeState + "'");
            return defaultLoopDelay;
        }

        return action.onLoop();
    }

    protected void onPaintManaged() {

    }

    protected void onPaintOverlayManaged() {

    }

    protected void onConVarChangedManaged(ConVar conv, int oldValue, int newValue) {

    }

    protected void onWidgetVisibilityChangedManaged(int id, boolean visible) {

    }

    protected void onLocalPlayerChangedManaged(Player player) {

    }

    protected void onInventoryItemChangedManaged(WidgetItem prev, WidgetItem next) {

    }

    protected void onEnabledManaged() {
        onEnabledManaged();
    }

    protected void onDisabledManaged() {
        onDisabledManaged();
    }

    protected boolean interruptBreakManaged() {
        return interruptBreakManaged();
    }

    @Override
    public final int onLoop() {
        if (waiting()) {
            return defaultLoopDelay;
        }

        if (hasOption(OPTION_LOOP_WAIT_LOCAL_PLAYER)) {
            self = Players.self();
            if (self == null) {
                return defaultLoopDelay;
            }
        }

        return onLoopManaged();
    }

    @Override
    public final void onPaint() {
        if (waiting()) {
            return;
        }

        onPaintManaged();
    }

    @Override
    public final void onPaintOverlay() {
        if (waiting()) {
            return;
        }

        onPaintOverlayManaged();
    }

    @Override
    public final void onConVarChanged(ConVar conv, int oldValue, int newValue) {
        if (waiting()) {
            return;
        }

        onConVarChangedManaged(conv, oldValue, newValue);
    }

    @Override
    public final void onWidgetVisibilityChanged(int id, boolean visible) {
        if (waiting()) {
            return;
        }

        onWidgetVisibilityChangedManaged(id, visible);
    }

    @Override
    public final void onLocalPlayerChanged(Player self) {
        if (waiting()) {
            return;
        }

        onLocalPlayerChangedManaged(self);
    }

    @Override
    public final void onInventoryItemChanged(WidgetItem prev, WidgetItem next) {
        if (waiting()) {
            return;
        }

        onInventoryItemChangedManaged(prev, next);
    }

    @Override
    public final void onEnabled() {
        onEnabledManaged();
    }

    @Override
    public final void onDisabled() {
        onDisabledManaged();
    }

    @Override
    public final boolean interruptBreak() {
        return interruptBreakManaged();
    }

    public interface PluginAction {
        default void onEnter() {

        }

        default int onLoop() {
            return 60000;
        }

        default void onExit() {

        }
    }
}
