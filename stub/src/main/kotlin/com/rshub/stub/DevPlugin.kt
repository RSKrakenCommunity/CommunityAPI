package com.rshub.stub

import com.javatar.plugin.api.PluginExtension
import kraken.plugin.api.*
import org.pf4j.DefaultPluginManager
import java.nio.file.Paths

class DevPlugin : Plugin() {

    private val manager = DefaultPluginManager(Paths.get(System.getProperty("user.home")).resolve("kraken-plugins"))

    override fun onLoaded(pluginContext: PluginContext): Boolean {
        pluginContext.name = "Local Plugins"
        pluginContext.isEnabled = true
        manager.loadPlugins()
        manager.startPlugins()
        Debug.log("Plugins Loaded ${manager.plugins.size}")
        manager.getExtensions(PluginExtension::class.java)?.forEach { it.onLoad() }
        return super.onLoaded(pluginContext)
    }

    override fun onEnabled() {
        manager.getExtensions(PluginExtension::class.java)?.forEach { it.enabled() }
    }

    override fun onDisabled() {
        manager.getExtensions(PluginExtension::class.java)?.forEach { it.disabled() }
    }

    override fun onLoop(): Int {
        manager.getExtensions(PluginExtension::class.java)?.forEach { it.onLoad() }
        return 600
    }

    override fun onPaint() {
        manager.getExtensions(PluginExtension::class.java)?.forEach { it.paint() }
    }

    override fun onPaintOverlay() {
        manager.getExtensions(PluginExtension::class.java)?.forEach { it.paintOverlay() }
    }

    override fun onConVarChanged(conv: ConVar, oldValue: Int, newValue: Int) {
        manager.getExtensions(PluginExtension::class.java)?.forEach { it.varChanged(conv, oldValue, newValue) }
    }

    override fun onWidgetVisibilityChanged(id: Int, visible: Boolean) {
        manager.getExtensions(PluginExtension::class.java)?.forEach { it.widgetVisibilityChanged(id, visible) }
    }

    override fun onLocalPlayerChanged(self: Player) {
        manager.getExtensions(PluginExtension::class.java)?.forEach { it.localPlayerChanged(self) }
    }

    override fun onInventoryItemChanged(prev: WidgetItem, next: WidgetItem) {
        manager.getExtensions(PluginExtension::class.java)?.forEach { it.inventoryItemChanged(prev, next) }
    }

    override fun interruptBreak(): Boolean {
        return manager.getExtensions(PluginExtension::class.java)?.any { it.shouldInterruptBreak() } ?: false
    }
}