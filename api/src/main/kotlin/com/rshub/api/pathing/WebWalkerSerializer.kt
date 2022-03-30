package com.rshub.api.pathing

import com.rshub.api.pathing.web.Graph
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kraken.plugin.api.Kraken
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.exists
import kotlin.io.path.readText

object WebWalkerSerializer {

    fun save() {
        val data = Json.encodeToString(WalkHelper.getGraph())
        Files.write(Paths.get(Kraken.getPluginDir()).resolve("web.json"), data.toByteArray())
    }

    fun load() {
        val webJson = Paths.get(Kraken.getPluginDir()).resolve("web.json")
        if(webJson.exists()) {
            val graph = Json.decodeFromString<Graph>(webJson.readText())
            WalkHelper.setGraph(graph)
        }
    }

}