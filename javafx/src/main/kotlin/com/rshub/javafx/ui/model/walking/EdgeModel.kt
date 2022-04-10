package com.rshub.javafx.ui.model.walking

import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.edges.strategies.DoorStrategy
import com.rshub.api.pathing.web.edges.strategies.EdgeTileStrategy
import com.rshub.api.pathing.web.edges.strategies.NpcStrategy
import com.rshub.api.pathing.web.edges.strategies.ObjectStrategy
import javafx.beans.property.SimpleObjectProperty
import kraken.plugin.api.Debug
import tornadofx.ViewModel

class EdgeModel(from: VertexModel, to: VertexModel, var edge: Edge) :
    ViewModel() {

    private val editor: VertexEditorModel by di()
    private val osEditor: ObjectStrategyEditorModel by di()
    private val npcEditor: NpcStrategyEditorModel by di()
    private val doorEditor: DoorEditorModel by di()

    val from = bind { SimpleObjectProperty(this, "from", from) }
    val to = bind { SimpleObjectProperty(this, "to", to) }
    val strategy = bind {
        SimpleObjectProperty(
            this, "strategy", when (edge.strategy) {
                is EdgeTileStrategy -> EdgeStrategyType.TILE
                is ObjectStrategy -> EdgeStrategyType.OBJECT
                is NpcStrategy -> EdgeStrategyType.NPC
                is DoorStrategy -> EdgeStrategyType.DOOR
                else -> error("Unknown strategy : ${edge.strategy::class.java.signers}")
            }
        )
    }

    fun update() {
        val canUpdate = when (strategy.get()!!) {
            EdgeStrategyType.TILE -> true
            EdgeStrategyType.OBJECT -> osEditor.objectId.get() != -1 && osEditor.objectX.get() != -1 && osEditor.objectY.get() != -1
            EdgeStrategyType.DOOR -> doorEditor.openDoorId.get() != -1 && doorEditor.openX.get() != -1 && doorEditor.openY.get() != -1 && doorEditor.openZ.get() != -1 && doorEditor.closedDoorId.get() != -1 && doorEditor.closedX.get() != -1 && doorEditor.closedY.get() != -1 && doorEditor.closedZ.get() != -1
            EdgeStrategyType.NPC -> npcEditor.npcId.get() != -1 && npcEditor.locX.get() != -1 && npcEditor.locY.get() != -1 && npcEditor.locZ.get() != -1
        }
        if (canUpdate) {
            edge = toEdge()
        }
    }

    fun toEdge(): Edge {
        val strategy = editor.createStrategy(this.strategy.get())
        val from = this.from.get().toVertex()
        val to = this.to.get().toVertex()
        return Edge(from, to, strategy)
    }

}