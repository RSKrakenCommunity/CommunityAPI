package com.rshub.javafx.ui.tabs

import com.rshub.api.pathing.walking.Traverse
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import com.rshub.javafx.ui.model.walking.LocationModel
import com.rshub.javafx.ui.model.walking.WalkingModel
import javafx.geometry.Orientation
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kraken.plugin.api.Kraken
import kraken.plugin.api.Players
import tornadofx.*
import java.nio.file.Paths
import kotlin.io.path.exists

class WalkingTab : Fragment("Walking") {

    private val model: WalkingModel by di()

    override val root = hbox {
        spacing = 10.0
        listview<LocationModel> {
            items.bind(model.locations) { it }
            bindSelected(model.selectedLocation)
            cellFormat {
                graphic = anchorpane {
                    label(it.name) {
                        anchorpaneConstraints {
                            leftAnchor = 0.0
                            topAnchor = 0.0
                            bottomAnchor = 0.0
                        }
                    }
                    button("Walk") {
                        anchorpaneConstraints {
                            rightAnchor = 0.0
                            topAnchor = 0.0
                            bottomAnchor = 0.0
                        }
                        setOnAction { _ ->
                            GlobalScope.launch {
                                Traverse.walkTo(it.tile.get())
                            }
                        }
                    }
                }
            }
        }
        separator(Orientation.VERTICAL)
        vbox {
            hbox {
                spacing = 5.0
                label("Location Name")
                textfield(model.locationName)
            }
            separator()
            button("Save Location") {
                disableWhen(model.locationName.isNull.or(model.locationName.isEmpty))
                setOnAction {
                    val player = Players.self()
                    if(player != null) {
                        val name = model.locationName.get()
                        model.locations.add(LocationModel(name, player.globalPosition.toTile()))
                        val path = Paths.get(System.getProperty("user.home")).resolve("kraken-plugins")
                        if(!path.exists()) {
                            path.toFile().mkdirs()
                        }
                        LocationModel.save(path)
                    }
                }
            }
        }
    }
}