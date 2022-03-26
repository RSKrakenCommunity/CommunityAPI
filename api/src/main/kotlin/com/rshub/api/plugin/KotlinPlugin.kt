package com.rshub.api.plugin

import kotlinx.coroutines.runBlocking
import kraken.plugin.api.Plugin
import kraken.plugin.api.PluginContext

abstract class KotlinPlugin(val name: String) : Plugin() {

    lateinit var context: PluginContext
        private set

    abstract fun onLoad()
    abstract suspend fun loop(): Int
    abstract suspend fun paint()
    abstract suspend fun paintOverlay()

    final override fun onLoaded(pluginContext: PluginContext): Boolean {
        pluginContext.name = name
        context = pluginContext
        onLoad()
        return super.onLoaded(pluginContext)
    }

    final override fun onLoop(): Int {
        return runBlocking { loop() }
    }

    final override fun onPaint() {
        runBlocking { paint() }
    }

    override fun onPaintOverlay() {
        runBlocking { paintOverlay() }
    }
}