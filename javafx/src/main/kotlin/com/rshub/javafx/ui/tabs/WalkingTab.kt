package com.rshub.javafx.ui.tabs

import com.rshub.definitions.maps.WorldTile.Companion.toTile
import com.rshub.javafx.ui.model.walking.LocationModel
import com.rshub.javafx.ui.model.walking.WalkingModel
import javafx.geometry.Orientation
import kraken.plugin.api.Players
import tornadofx.*

class WalkingTab : Fragment("Walking") {

    private val model: WalkingModel by di()

    override val root = hbox {
        spacing = 10.0
        LocationModel.load()
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
                        setOnAction {
                            TODO("Not yet implemented")
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
                        LocationModel.save()
                    }
                }
            }
        }
    }
}