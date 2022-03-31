package com.javatar.plugin.api

import kraken.plugin.api.ConVar
import kraken.plugin.api.Player
import kraken.plugin.api.WidgetItem
import org.pf4j.ExtensionPoint

interface PluginExtension : ExtensionPoint {
    fun onLoad(): Boolean {
        return true
    }

    /**
     * Called when this plugin is enabled in the plugin list.
     */
    fun enabled() {}

    /**
     * Called when this plugin is disabled in the plugin list.
     */
    fun disabled() {}

    /**
     * Called when the client is ticking, and it's time for us
     * to loop again. The client will wait for the amount of milliseconds
     * you return before calling this function again.
     *
     * @return The amount of time to wait before invoking this function again.
     */
    fun loop(): Int {
        return 60000
    }

    /**
     * Called when the plugin's window is being painted.
     */
    fun paint() {}

    /**
     * Called when the client's 3d overlay is being painted.
     */
    fun paintOverlay() {}

    /**
     * Called when a connection variable changes.
     */
    fun varChanged(conv: ConVar, oldValue: Int, newValue: Int) {}

    /**
     * Called when the visibility of a widget changes.
     */
    fun widgetVisibilityChanged(id: Int, visible: Boolean) {}

    /**
     * Called when the local player is changed. This is useful for initializing plugin data
     * about the local player.
     */
    fun localPlayerChanged(self: Player) {}

    /**
     * Called when an item in the inventory is changed.
     */
    fun inventoryItemChanged(prev: WidgetItem, next: WidgetItem) {}

    /**
     * Called to determine if a break should be interrupted currently.
     */
    fun shouldInterruptBreak(): Boolean {
        return false
    }
}