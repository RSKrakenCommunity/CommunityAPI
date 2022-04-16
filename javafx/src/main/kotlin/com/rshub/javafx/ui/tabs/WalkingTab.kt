package com.rshub.javafx.ui.tabs

import com.rshub.api.pathing.walking.Traverse
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import com.rshub.javafx.ui.model.walking.LocationModel
import com.rshub.javafx.ui.model.walking.WalkingModel
import javafx.collections.FXCollections
import javafx.embed.swt.FXCanvas
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.ButtonType
import javafx.scene.control.CheckBox
import kotlinx.coroutines.*
import kraken.plugin.api.Kraken
import kraken.plugin.api.Players
import org.koin.core.context.GlobalContext
import tornadofx.*
import java.nio.file.Paths
import kotlin.io.path.exists

class WalkingTab : Fragment("Walking") {

    private val model: WalkingModel by di()

    override val root = vbox {
        spacing = 2.5
        hbox {
            spacing = 5.0
            paddingAll = 5.0
            alignment = Pos.CENTER_LEFT
            textfield(model.locationName) {
                promptText = "Enter Location Name"
            }
            checkbox("Bank Location", model.isBank)
            separator(Orientation.VERTICAL)
            button("Add Location") {
                disableWhen(model.locationName.isEmpty.or(model.locationName.isNull))
            }.setOnAction {
                val player = Players.self() ?: return@setOnAction
                val name = model.locationName.get()
                val isBank = model.isBank.get()
                val tile = player.globalPosition.toTile()
                model.locations.add(LocationModel(name, tile, isBank))
            }
            button("Save Locations").setOnAction { save() }
            separator(Orientation.VERTICAL)
            checkbox("Show Bank on Minimap", model.showBanksOnMinimap)
        }
        tableview<LocationModel> {
            items.bind(model.locations) { it }
            fitToParentSize()
            smartResize()
            column("Name", LocationModel::name).cellFormat {
                graphic = hbox {
                    alignment = Pos.CENTER_LEFT
                    textfield(rowItem.name)
                }
            }
            column("Tile", LocationModel::tile)
            column("Bank Location", LocationModel::bank).cellFormat {
                graphic = hbox {
                    alignment = Pos.CENTER
                    checkbox(property = rowItem.bank)
                }
            }
            column<LocationModel, Unit>("Actions") { Unit.toProperty() }
                .cellFormat {
                    graphic = hbox {
                        spacing = 10.0
                        alignment = Pos.CENTER
                        button("Walk").setOnAction {
                            GlobalScope.launch(Dispatchers.Default) {
                                if(!Traverse.walkTo(rowItem.tile.get())) {
                                    runLater {
                                        warning("Walk to ${rowItem.name.get()}", "Unable to walk here.")
                                    }
                                }
                            }
                        }
                        button("Delete").setOnAction {
                            confirm("Delete Location ${rowItem.name.get()}", "Are you sure?", ButtonType.YES, ButtonType.NO, title = "Delete Location") {
                                model.locations.remove(rowItem)
                            }
                        }
                    }
                }
        }
    }

    private fun save() {
        val path = Paths.get(System.getProperty("user.home")).resolve("kraken-plugins")
        if (!path.exists()) {
            path.toFile().mkdirs()
        }
        LocationModel.save(path)
    }
}