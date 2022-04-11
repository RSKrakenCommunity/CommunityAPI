package com.rshub.javafx.ui.model.walking

import com.rshub.definitions.maps.WorldTile
import com.rshub.json.serialization.LocationModelSerializer
import javafx.beans.property.SimpleBooleanProperty
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
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.exists
import kotlin.io.path.readText

@Serializable(with = LocationModelSerializer::class)
class LocationModel(name: String, tile: WorldTile, isBank: Boolean) : ViewModel() {

    val name = bind { SimpleStringProperty(this, "name", name) }
    val bank = bind { SimpleBooleanProperty(this, "bank", isBank) }
    val tile = bind { SimpleObjectProperty(this, "tile", tile) }


    companion object : KoinComponent {
        private val json = Json { prettyPrint = true }
        fun save(path: Path) {
            val model: WalkingModel = get()
            val data = json.encodeToString(model.locations.toList())
            val bankData = json.encodeToString(model.locations.filter { it.bank.get() })
            Files.write(path.resolve("locations.json"), data.toByteArray())
            Files.write(path.resolve("banks.json"), bankData.toByteArray())
        }
        fun load(path: Path) {
            val locs = path.resolve("locations.json")
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