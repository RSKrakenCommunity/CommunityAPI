package com.rshub.stud

import kraken.plugin.api.ImGui
import kraken.plugin.api.Plugin
import kraken.plugin.api.PluginContext

class StubEntry : Plugin() {
    override fun onLoaded(pluginContext: PluginContext): Boolean {
        pluginContext.name = "Local Plugins"
        pluginContext.isEnabled = true
        return super.onLoaded(pluginContext)
    }

    override fun onPaint() {
        ImGui.label("Authors: Javatar")
    }
}