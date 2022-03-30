package com.rshub.stub

import com.rshub.api.definitions.DefinitionManager.Companion.def
import com.rshub.api.map.ClipFlag
import com.rshub.api.map.Region
import com.rshub.api.pathing.WalkHelper
import com.rshub.api.plugin.KotlinPlugin
import com.rshub.api.services.GameStateHelper
import com.rshub.api.world.WorldHelper
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.localX
import com.rshub.definitions.maps.WorldTile.Companion.localY
import com.rshub.definitions.maps.WorldTile.Companion.regionId
import com.rshub.javafx.DebugUI
import com.rshub.javafx.ui.model.VariableDebuggerModel
import com.rshub.javafx.ui.model.VariableModel
import com.rshub.stub.services.PlayerUpdateService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kraken.plugin.api.Client
import kraken.plugin.api.ConVar
import kraken.plugin.api.ImGui
import kraken.plugin.api.Players
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import tornadofx.launch
import tornadofx.runLater
import java.awt.Color
import java.io.FileOutputStream
import java.io.PrintStream
import java.nio.file.Paths
import kotlin.io.path.exists

class DevPlugin : KotlinPlugin("Dev Plugin"), KoinComponent {

    val start = WorldTile(3093, 3494, 0)
    val target = WorldTile(3087, 3502, 0)

    private val errorPath = Paths.get("error.txt")
    private val errorOutput = PrintStream(FileOutputStream(errorPath.toFile()))

    override fun onLoad() {
        context.isEnabled = true
        if (!errorPath.exists()) {
            errorPath.toFile().createNewFile()
        }
        System.setOut(errorOutput)
        System.setErr(errorOutput)
        startKoin {
            modules(DebugUI.fxModule)
        }
        GameStateHelper.registerService(PlayerUpdateService())
        GlobalScope.launch {
            launch<DebugUI>()
        }
    }

    override suspend fun loop(): Int {
        return 600
    }

    override suspend fun paint() {
        ImGui.label("Authors: Javatar")
        ImGui.label("Credits: Trent (FreeDev)")
        val player = Players.self()
        if (player != null) {
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
        if (player != null) {
            val pos = player.globalPosition
            val r = Region.get(pos.regionId)
            if (r.def != null) {
                val settings = r.def.settings[pos.z][pos.localX][pos.localY]
                val mask = r.clipMap.masks[pos.z][pos.localX][pos.localY]
                ImGui.label("Tile Settings $settings")
                ImGui.label("Tile Mask: $mask")
                for (value in ClipFlag.values) {
                    if (value != null && (mask and value.flag) != 0) {
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

    override suspend fun paintOverlay() {
        val graph = WalkHelper.getGraph()
        for ((id, vertex) in graph.getAllVertices().withIndex()) {
            val pos = Client.worldToMinimap(vertex.tile.toScene())
            ImGui.freeText("$id", pos, Color.WHITE.rgb)
            val abjs = graph.adjacentVertices(vertex)
            if (abjs.isNotEmpty()) {
                for (abj in abjs) {
                    val end = Client.worldToMinimap(abj.tile.toScene())
                    ImGui.freeLine(pos, end, Color.GREEN.rgb)
                }
            }
        }
    }

    override fun onConVarChanged(conv: ConVar, oldValue: Int, newValue: Int) {
        runLater {
            val vd: VariableDebuggerModel = get()
            if (vd.varps.containsKey(conv.id)) {
                vd.varps[conv.id]?.value?.set(newValue)
            } else if (!vd.isScanMode.get()) {
                vd.varps[conv.id] = VariableModel(conv.id, "Varp ${conv.id}", newValue)
            }
        }
    }
}