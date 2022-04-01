package com.rshub.javafx.ui.model.walking

import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.edges.strategies.EdgeTileStrategy
import com.rshub.api.pathing.web.edges.strategies.NpcStrategy
import com.rshub.api.pathing.web.edges.strategies.ObjectStrategy
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel

class EdgeModel(from: VertexModel, to: VertexModel, strategy: EdgeStrategy = EdgeStrategy.TILE) : ViewModel() {

    private val osEditor: ObjectStrategyEditorModel by di()
    private val npcEditor: NpcStrategyEditorModel by di()

    val from = bind { SimpleObjectProperty(this, "from", from) }
    val to = bind { SimpleObjectProperty(this, "to", to) }
    val strategy = bind { SimpleObjectProperty(this, "strategy", strategy) }

    fun toEdge(): Edge {
        val strategy = when(strategy.get()!!) {
            EdgeStrategy.TILE -> EdgeTileStrategy()
            EdgeStrategy.OBJECT -> {
                ObjectStrategy(
                    osEditor.objectId.get(),
                    osEditor.objectX.get(),
                    osEditor.objectY.get(),
                    osEditor.objectZ.get(),
                    osEditor.action.get(),
                    osEditor.skill.get(),
                    osEditor.level.get()
                )
            }
            EdgeStrategy.NPC -> {
                NpcStrategy(
                    npcEditor.npcId.get(),
                    npcEditor.action.get()
                )
            }
        }
        val from = this.from.get().toVertex()
        val to = this.to.get().toVertex()
        return Edge(from, to, strategy)
    }

}