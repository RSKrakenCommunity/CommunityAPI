package com.rshub.stub

import com.rshub.api.definitions.DefinitionManager.Companion.def
import com.rshub.api.map.ClipFlag
import com.rshub.api.map.Region
import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.strategy.FixedTileStrategy
import com.rshub.api.world.WorldHelper
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.localX
import com.rshub.definitions.maps.WorldTile.Companion.localY
import com.rshub.definitions.maps.WorldTile.Companion.regionId
import com.rshub.javafx.DebugUI
import com.rshub.javafx.ui.DebugView
import kotlinx.coroutines.*
import kraken.plugin.api.*
import java.awt.Color
import java.io.FileOutputStream
import java.io.PrintStream
import java.nio.file.Paths
import kotlin.io.path.exists

class StubEntry : Plugin() {

    val start = WorldTile(3093, 3494, 0)
    val target = WorldTile(3087, 3502, 0)

    private val errorPath = Paths.get("error.txt")
    private val errorOutput = PrintStream(FileOutputStream(errorPath.toFile()))

    override fun onLoaded(pluginContext: PluginContext): Boolean {
        pluginContext.name = "Developer Plugin"
        pluginContext.isEnabled = true
        if(!errorPath.exists()) {
            errorPath.toFile().createNewFile()
        }
        System.setOut(errorOutput)

        GlobalScope.launch {
            tornadofx.launch<DebugUI>()
        }
        return super.onLoaded(pluginContext)
    }

    override fun onPaint() {
        ImGui.label("Authors: Javatar")
        ImGui.label("Credits: Trent (FreeDev)")
        val player = Players.self()
        if(player != null) {
            ImGui.label("Region: ${player.globalPosition.regionId}")
            ImGui.label("Global: (${player.globalPosition.x}, ${player.globalPosition.y}. ${player.globalPosition.z})")
            ImGui.label("Local: (${player.globalPosition.localX}, ${player.globalPosition.localY}, ${player.globalPosition.z})")
        }
        val wnpc = WorldHelper.closestNpc { it.name.equals("Doris", true) }
        if (wnpc != null) {
            ImGui.label("ID ${wnpc.id} - ${wnpc.name} - ${wnpc.getTile()}")
        }
        val wobj = WorldHelper.closestObject { it.id == 12269 }
        if (wobj != null) {
            ImGui.label("ID ${wobj.id} - ${wobj.name} - ${wobj.getTile()}")
        }
        if(player != null) {
            val pos = player.globalPosition
            val r = Region.get(pos.regionId)
            if (r.def != null) {
                val settings = r.def.settings[pos.z][pos.localX][pos.localY]
                val mask = r.clipMap.masks[pos.z][pos.localX][pos.localY]
                ImGui.label("Tile Settings $settings")
                ImGui.label("Tile Mask: $mask")
                for (value in ClipFlag.values) {
                    if(value != null && (mask and value.flag) != 0) {
                        ImGui.label("\tFlag ${value.name}")
                    }
                }
                for (mo in r.def.objects) {
                    val lt = mo.localTile
                    if (lt.x == pos.localX && lt.y == pos.localY) {
                        ImGui.label("\tObject#${mo.objectId} - ${mo.def.name} - Height ${lt.z}")
                        ImGui.label("\t\tBlock Projectile ${mo.def.blocksProjectile}")
                        ImGui.label("\t\tClip Type ${mo.def.solidType}")
                        ImGui.label("\t\tTypes ${mo.def.types.contentToString()}")
                    }
                }
            }
        }

    }

    override fun onPaintOverlay() {
        runBlocking {
            val tiles = LocalPathing.findLocalRoute(start, 1, FixedTileStrategy(target), false)
            if(tiles != null && tiles.isNotEmpty()) {
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
            delay(5)
        }
    }
}