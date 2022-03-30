package com.rshub.javafx.ui.tabs

import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import com.rshub.javafx.ui.model.walking.*
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import kraken.plugin.api.Players
import tornadofx.*

class WebWalkingTab : Fragment("Web Walking") {

    private val model: WebWalkingModel by di()
    private val editor: VertexEditorModel by di()

    override val root = hbox {
        spacing = 10.0
        vbox {
            alignment = Pos.CENTER_LEFT
            hbox {
                alignment = Pos.CENTER
                checkbox("Auto Update", model.autoUpdate) {
                    paddingRight = 5.0
                }
                button("Add Vertex") {
                    setOnAction {
                        val tile = if(editor.usePlayerTile.get()) {
                            Players.self()?.globalPosition?.toTile()
                        } else {
                            val x = editor.tileX.get()
                            val y = editor.tileY.get()
                            val z = editor.tileZ.get()
                            WorldTile(x, y, z)
                        }
                        if(tile != null) {
                            val id = model.vertices.size
                            model.vertices.add(VertexModel(id, tile))
                        }
                        if(model.autoUpdate.get()) {
                            model.update()
                        }
                    }
                }
                separator(Orientation.VERTICAL)
                button("Update Vertices") {
                    disableWhen(model.autoUpdate)
                    setOnAction {
                        model.update()
                    }
                }
            }
            listview<VertexModel> {
                fitToParentHeight()
                items.bind(model.vertices) { it }
                bindSelected(model.selectedVertex)
                model.selectedVertex.onChange {
                    if (it != null) {
                        if(model.edges.isBound) {
                            model.edges.unbind()
                        }
                        model.edges.bind(it.edges)
                    }
                }
                model.reload()
                cellFormat {
                    text = "${it.id.get()} - ${it.edges.size}"
                }
            }
        }
        separator(Orientation.VERTICAL)
        vbox {
            paddingAll = 10.0
            spacing = 10.0
            checkbox("Use Player Tile", editor.usePlayerTile)
            vbox {
                spacing = 10.0
                dynamicContent(editor.usePlayerTile) {
                    if(it == false) {
                        hbox {
                            disableWhen(editor.usePlayerTile)
                            spacing = 2.0
                            label("Tile X")
                            textfield(editor.tileX) {
                                stripNonInteger()
                            }
                        }
                        hbox {
                            disableWhen(editor.usePlayerTile)
                            spacing = 2.0
                            label("Tile Y")
                            textfield(editor.tileY) {
                                stripNonInteger()
                            }
                        }
                        hbox {
                            disableWhen(editor.usePlayerTile)
                            spacing = 2.0
                            label("Tile Z")
                            textfield(editor.tileZ) {
                                stripNonInteger()
                            }
                        }
                    }
                }
            }
        }
        separator(Orientation.VERTICAL)
        hbox {
            vbox {
                dynamicContent(editor.strategy) {
                    nodesByStrategy(it)
                }
            }
            listview<EdgeModel> {
                items.bind(model.edges) { it }
                bindSelected(model.selectedEdge)
                cellFormat {
                    text = "Edge( From: ${it.from.get().id.get()} To: ${it.to.get().id.get()} ) - ${it.strategy.get().name}"
                }
            }
        }
    }

    private fun VBox.nodesByStrategy(strategy: EdgeStrategy?) {
        if(strategy == null) return
        when(strategy) {
            EdgeStrategy.TILE -> {
                label("Tile strategy has no configuration.")
            }
            EdgeStrategy.OBJECT -> TODO()
            EdgeStrategy.AGILITY -> TODO()
        }
    }
}