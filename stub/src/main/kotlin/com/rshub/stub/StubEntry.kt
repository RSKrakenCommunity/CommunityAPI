package com.rshub.stub

import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.strategy.FixedTileStrategy
import com.rshub.api.world.WorldHelper
import com.rshub.definitions.maps.WorldTile
import kraken.plugin.api.Client
import kraken.plugin.api.ImGui
import kraken.plugin.api.Plugin
import kraken.plugin.api.PluginContext
import java.awt.Color
import java.io.PrintStream

class StubEntry : Plugin() {

    val start = WorldTile(3094, 3491, 0)
    val target = WorldTile(3079, 3494, 0)
    val tiles = mutableListOf<WorldTile>()

    override fun onLoaded(pluginContext: PluginContext): Boolean {
        pluginContext.name = "Developer Plugin"
        pluginContext.isEnabled = true
        val tiles = LocalPathing.findLocalRoute(start, 1, FixedTileStrategy(target), false)
        this.tiles.addAll(tiles)
        return super.onLoaded(pluginContext)
    }

    override fun onPaint() {
        ImGui.label("Authors: Javatar")
        ImGui.label("Credits: Trent (FreeDev)")
        val wnpc = WorldHelper.closestNpc { it.name.equals("Skeleton", true) }
        if (wnpc != null) {
            ImGui.label("ID ${wnpc.id} - ${wnpc.name} - ${wnpc.getTile()}")
        }
        val wobj = WorldHelper.closestObject { it.id == 12269 }
        if (wobj != null) {
            ImGui.label("ID ${wobj.id} - ${wobj.name} - ${wobj.getTile()}")
        }
    }

    override fun onPaintOverlay() {
        if(tiles.isNotEmpty()) {
            repeat(tiles.size) {
                val n1 = (it + 1)
                if(n1 < tiles.size) {
                    val curr = tiles[it]
                    val next = tiles[n1]
                    val mini1 = Client.worldToMinimap(curr.toScene())
                    val mini2 = Client.worldToMinimap(next.toScene())
                    ImGui.freeLine(mini1, mini2, Color.GREEN.rgb)
                }
            }
        }
    }
}