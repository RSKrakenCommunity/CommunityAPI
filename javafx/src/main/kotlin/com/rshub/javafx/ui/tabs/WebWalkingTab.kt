package com.rshub.javafx.ui.tabs

import com.rshub.api.actions.NpcAction
import com.rshub.api.actions.ObjectAction
import com.rshub.api.pathing.WalkHelper
import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.nodes.GraphVertex
import com.rshub.api.skills.Skill
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import com.rshub.javafx.ui.model.walking.*
import javafx.beans.binding.Bindings
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import javafx.util.StringConverter
import kraken.plugin.api.Players
import kraken.plugin.api.Vector3i
import tornadofx.*

class WebWalkingTab : Fragment("Web Walking") {

    private val model: WebWalkingModel by di()
    private val editor: VertexEditorModel by di()
    private val osEditor: ObjectStrategyEditorModel by di()
    private val npcEditor: NpcStrategyEditorModel by di()
    private val doorEditor: DoorEditorModel by di()

    override val root = hbox {
        spacing = 10.0
        vbox {
            alignment = Pos.CENTER_LEFT
            vbox {
                hbox {
                    alignment = Pos.CENTER
                    button("Add Vertex") {
                        setOnAction {
                            val tile = if (editor.usePlayerTile.get()) {
                                Players.self()?.globalPosition?.toTile()
                            } else {
                                val x = editor.tileX.get()
                                val y = editor.tileY.get()
                                val z = editor.tileZ.get()
                                WorldTile(x, y, z)
                            }
                            if (tile != null) {
                                val id = model.vertices.size
                                model.vertices.add(VertexModel(id, GraphVertex(tile)))
                            }
                            if (model.autoUpdate.get()) {
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
                            if (model.autoUpdate.get()) {
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
                    button("Save Vertices") {
                        setOnAction {
                            WalkHelper.saveWeb()
                        }
                    }
                    button("Load Web") {
                        setOnAction {
                            WalkHelper.loadWeb()
                            model.reload()
                        }
                    }
                }
            }
            listview<VertexModel> {
                fitToParentHeight()
                items.bind(model.vertices) { it }
                bindSelected(model.selectedVertex)
                items.onChange {
                    if (it.next() && it.wasAdded()) {
                        val added = it.addedSubList.firstOrNull()
                        if (added != null) {
                            val prevSel = model.selectedVertex.get()
                            selectionModel.select(added)
                            if (model.autoLink.get() && editor.strategy.get() === EdgeStrategyType.TILE) {
                                linkEdges(prevSel)
                            }
                        }
                    }
                }
                model.selectedVertex.onChange {
                    if (it != null) {
                        if (model.edges.isBound) {
                            model.edges.unbind()
                        }
                        model.edges.bind(it.edges)
                    }
                }
                model.reload()
                cellFormat {
                    graphic = anchorpane {
                        vbox {
                            spacing = 2.5
                            alignment = Pos.CENTER_LEFT
                            anchorpaneConstraints { leftAnchor = 0.0; topAnchor = 0.0; bottomAnchor = 0.0 }
                            label {
                                textProperty().bind(Bindings.createStringBinding({
                                    "Vertex ${it.id.get()} - ${it.edges.size}"
                                }, it.edges.sizeProperty()))
                            }
                            label(it.tile)
                        }
                        hbox {
                            spacing = 10.0
                            anchorpaneConstraints { rightAnchor = 0.0; topAnchor = 0.0; bottomAnchor = 0.0 }
                            button("Link") {
                                disableWhen(model.selectedVertex.isNull.or(model.selectedVertex.isEqualTo(it)))
                                setOnAction { _ ->
                                    linkEdges(it)
                                }
                                Vector3i(0, 0, 0).toTile()
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
            checkbox("Show Minimap", model.showOnMinimap)
            checkbox("Use Player Tile", editor.usePlayerTile)
            checkbox("Auto Link") {
                disableWhen(editor.strategy.isNotEqualTo(EdgeStrategyType.TILE))
                bind(model.autoLink)
            }
            checkbox("Auto Update", model.autoUpdate) {
                paddingRight = 5.0
            }
            vbox {
                spacing = 10.0
                dynamicContent(editor.usePlayerTile) {
                    if (it == false) {
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
                items.addAll(EdgeStrategyType.values())
                selectionModel.select(EdgeStrategyType.TILE)
                converter = object : StringConverter<EdgeStrategyType>() {
                    override fun toString(strat: EdgeStrategyType): String {
                        return strat.name.lowercase().capitalize().replace('_', ' ')
                    }

                    override fun fromString(string: String): EdgeStrategyType {
                        return EdgeStrategyType.valueOf(
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
                    text =
                        "Edge( From: ${it.from.get().id.get()} To: ${it.to.get().id.get()} ) - ${it.strategy.get().name}"
                }
            }
        }
    }

    private fun linkEdges(it: VertexModel) {
        val sel = model.selectedVertex.get()
        if (sel != null) {
            val strat = editor.strategy.get()
            val edge = Edge(sel.toVertex(), it.toVertex(), editor.createStrategy())
            val edgeModel = EdgeModel(sel, it, edge)
            sel.edges.add(edgeModel)
            if (strat === EdgeStrategyType.TILE) {
                it.edges.add(EdgeModel(it, sel, edge))
            }
            if (model.autoUpdate.get()) {
                model.update()
            }
        }
    }

    private fun VBox.nodesByStrategy(strategy: EdgeStrategyType?) {
        if (strategy == null) return
        when (strategy) {
            EdgeStrategyType.TILE -> {
                label("Tile strategy has no configuration.")
            }
            EdgeStrategyType.OBJECT -> {
                form {
                    fieldset("Object Interaction") {
                        field("Object ID") {
                            textfield(osEditor.objectId) {
                                stripNonInteger()
                            }
                        }
                        field("Object X") {
                            textfield(osEditor.objectX) {
                                stripNonInteger()
                            }
                        }
                        field("Object Y") {
                            textfield(osEditor.objectY) {
                                stripNonInteger()
                            }
                        }
                        field("Object Z") {
                            textfield(osEditor.objectZ) {
                                stripNonInteger()
                            }
                        }
                        field("Object Action") {
                            choicebox(osEditor.action) {
                                items.addAll(ObjectAction.values())
                                selectionModel.select(ObjectAction.OBJECT1)
                                converter = object : StringConverter<ObjectAction>() {
                                    override fun toString(strat: ObjectAction): String {
                                        return strat.name.lowercase().capitalize().replace('_', ' ')
                                    }

                                    override fun fromString(string: String): ObjectAction {
                                        return ObjectAction.valueOf(
                                            string
                                                .uppercase()
                                                .replace(' ', '_')
                                        )
                                    }
                                }
                            }
                        }
                    }
                    fieldset("Skill Requirement") {
                        field("Skill") {
                            choicebox(osEditor.skill) {
                                items.addAll(Skill.values())
                                selectionModel.select(Skill.NONE)
                                converter = object : StringConverter<Skill>() {
                                    override fun toString(strat: Skill): String {
                                        return strat.name.lowercase().capitalize().replace('_', ' ')
                                    }

                                    override fun fromString(string: String): Skill {
                                        return Skill.valueOf(
                                            string
                                                .uppercase()
                                                .replace(' ', '_')
                                        )
                                    }
                                }
                            }
                        }
                        field("Level") {
                            textfield(osEditor.level) {
                                disableWhen(osEditor.skill.isEqualTo(Skill.NONE))
                                stripNonInteger()
                            }
                        }
                    }
                }
            }
            EdgeStrategyType.NPC -> {
                form {
                    fieldset("Npc Interaction") {
                        field("Npc ID") {
                            textfield(npcEditor.npcId)
                        }
                        field("Action") {
                            choicebox(npcEditor.action) {
                                items.addAll(NpcAction.values())
                                selectionModel.select(NpcAction.NPC1)
                                converter = object : StringConverter<NpcAction>() {
                                    override fun toString(value: NpcAction): String {
                                        return value.name.lowercase().capitalize()
                                            .replace('_', ' ')
                                    }

                                    override fun fromString(string: String): NpcAction {
                                        return NpcAction.valueOf(string.uppercase().replace(' ', '_'))
                                    }
                                }
                            }
                        }
                    }
                }
            }
            EdgeStrategyType.DOOR -> {
                form {
                    fieldset("Door Interaction") {
                        field("Object ID") {
                            textfield(doorEditor.objectId) {
                                stripNonInteger()
                            }
                        }
                        field("Object X") {
                            textfield(doorEditor.objectX)
                        }
                        field("Object Y") {
                            textfield(doorEditor.objectY)
                        }
                        field("Object Z") {
                            textfield(doorEditor.objectZ)
                        }
                        field("Action") {
                            choicebox(doorEditor.action) {
                                items.addAll(ObjectAction.values())
                                selectionModel.select(ObjectAction.OBJECT1)
                                converter = object : StringConverter<ObjectAction>() {
                                    override fun toString(value: ObjectAction): String {
                                        return value.name.lowercase().capitalize()
                                            .replace('_', ' ')
                                    }

                                    override fun fromString(string: String): ObjectAction {
                                        return ObjectAction.valueOf(string.uppercase().replace(' ', '_'))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}