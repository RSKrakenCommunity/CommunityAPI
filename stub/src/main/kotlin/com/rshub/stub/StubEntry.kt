package com.rshub.stub

import kraken.plugin.api.ImGui
import kraken.plugin.api.Plugin
import kraken.plugin.api.PluginContext

class StubEntry : Plugin() {
    override fun onLoaded(pluginContext: PluginContext): Boolean {
        pluginContext.name = "Developer Plugin"
        pluginContext.isEnabled = true
        return super.onLoaded(pluginContext)
    }

    override fun onPaint() {
        ImGui.label("Authors: Javatar")
        ImGui.label("Credits: Trent (FreeDev)")
    }
}