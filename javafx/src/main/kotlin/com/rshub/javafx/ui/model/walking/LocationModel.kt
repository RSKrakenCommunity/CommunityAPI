package com.rshub.javafx.ui.model.walking

import com.rshub.definitions.maps.WorldTile
import com.rshub.json.serialization.LocationModelSerializer
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kraken.plugin.api.Kraken
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import tornadofx.ViewModel
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.exists
import kotlin.io.path.readText

@Serializable(with = LocationModelSerializer::class)
class LocationModel(name: String, tile: WorldTile) : ViewModel() {

    val name = bind { SimpleStringProperty(this, "name", name) }
    val tile = bind { SimpleObjectProperty(this, "tile", tile) }


    companion object : KoinComponent {
        fun save() {
            val model: WalkingModel = get()
            val data = Json.encodeToString(model.locations.toList())
            Files.write(Paths.get(Kraken.getPluginDir()).resolve("locations.json"), data.toByteArray())
        }
        fun load() {
            val locs = Paths.get(Kraken.getPluginDir()).resolve("locations.json")
            if(locs.exists()) {
                val model: WalkingModel = get()
                val locations = Json.decodeFromString<List<LocationModel>>(locs.readText())
                if(model.locations.isNotEmpty()) {
                    model.locations.clear()
                }
                model.locations.addAll(locations)
            }
        }
    }
}