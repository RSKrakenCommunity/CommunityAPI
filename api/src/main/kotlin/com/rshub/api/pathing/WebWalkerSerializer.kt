package com.rshub.api.pathing

import com.rshub.api.pathing.web.Graph
import com.rshub.api.pathing.web.edges.EdgeStrategy
import com.rshub.api.pathing.web.edges.strategies.DoorStrategy
import com.rshub.api.pathing.web.edges.strategies.EdgeTileStrategy
import com.rshub.api.pathing.web.edges.strategies.NpcStrategy
import com.rshub.api.pathing.web.edges.strategies.ObjectStrategy
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.readText

object WebWalkerSerializer {

    private val edgeStrategyModule = SerializersModule {
        polymorphic(EdgeStrategy::class) {
            subclass(EdgeTileStrategy::class)
            subclass(ObjectStrategy::class)
            subclass(NpcStrategy::class)
            subclass(DoorStrategy::class)
        }
    }

    private val json = Json { prettyPrint = true; serializersModule = edgeStrategyModule }

    fun save(path: Path) {
        val data = json.encodeToString(WalkHelper.getGraph())
        Files.write(path.resolve("web.json"), data.toByteArray())
    }

    fun load(path: Path) {
        val webJson = path.resolve("web.json")
        if (webJson.exists()) {
            val graph = json.decodeFromString<Graph>(webJson.readText())
            WalkHelper.setGraph(graph)
        }
    }

}