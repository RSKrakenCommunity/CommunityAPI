package com.rshub.javafx.ui.tabs

import com.rshub.api.pathing.WalkHelper
import com.rshub.api.pathing.WebWalkerSerializer
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import com.rshub.javafx.ui.model.walking.*
import javafx.beans.binding.Bindings
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import javafx.util.StringConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kraken.plugin.api.Players
import tornadofx.*
import java.nio.file.Files
import java.nio.file.Paths

class WebWalkingTab : Fragment("Web Walking") {

    private val model: WebWalkingModel by di()
    private val editor: VertexEditorModel by di()

    override val root = hbox {
        spacing = 10.0
        vbox {
            alignment = Pos.CENTER_LEFT
            vbox {
                hbox {
                    alignment = Pos.CENTER
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
                    button("Remove Vertex") {
                        disableWhen(model.selectedVertex.isNull)
                        setOnAction {
                            val sel = model.selectedVertex.get()
                            val vertices = model.vertices.filter { it.edges.any { e -> e.to.get() === sel } }
                            for (vertex in vertices) {
                                vertex.edges.removeIf { it.to.get() === sel }
                            }
                            model.vertices.remove(sel)
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
                hbox {
                    alignment = Pos.CENTER
                    checkbox("Auto Update", model.autoUpdate) {
                        paddingRight = 5.0
                    }
                    button("Save Vertices") {
                        setOnAction {
                            WalkHelper.saveWeb()
                        }
                    }
                    button("Load Web") {
                        setOnAction {
                            WalkHelper.loadWeb()
                        }
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
                    graphic = anchorpane {
                        label {
                            textProperty().bind(Bindings.createStringBinding({
                                "Vertex ${it.id.get()} - ${it.edges.size}"
                            }, it.edges.sizeProperty()))
                            anchorpaneConstraints { leftAnchor = 0.0; topAnchor = 0.0; bottomAnchor = 0.0 }
                        }
                        hbox {
                            spacing = 10.0
                            anchorpaneConstraints { rightAnchor = 0.0; topAnchor = 0.0; bottomAnchor = 0.0 }
                            button("Link") {
                                disableWhen(model.selectedVertex.isNull.or(model.selectedVertex.isEqualTo(it)))
                                setOnAction { _ ->
                                    val sel = model.selectedVertex.get()
                                    if(sel != null) {
                                        val strat = editor.strategy.get()
                                        val edge = EdgeModel(sel, it, strat)
                                        sel.edges.add(edge)
                                        if(strat === EdgeStrategy.TILE) {
                                            it.edges.add(EdgeModel(it, sel))
                                        }
                                        if(model.autoUpdate.get()) {
                                            model.update()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        separator(Orientation.VERTICAL)
        vbox {
            paddingAll = 10.0
            spacing = 10.0
            minWidth = 200.0
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
            choicebox(editor.strategy) {
                items.addAll(EdgeStrategy.values())
                selectionModel.select(EdgeStrategy.TILE)
                converter = object : StringConverter<EdgeStrategy>() {
                    override fun toString(strat: EdgeStrategy): String {
                        return strat.name.lowercase().capitalize().replace('_', ' ')
                    }

                    override fun fromString(string: String): EdgeStrategy {
                        return EdgeStrategy.valueOf(
                            string
                                .uppercase()
                                .replace(' ', '_')
                        )
                    }
                }
            }
        }
        separator(Orientation.VERTICAL)
        hbox {
            alignment = Pos.CENTER_RIGHT
            spacing = 10.0
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