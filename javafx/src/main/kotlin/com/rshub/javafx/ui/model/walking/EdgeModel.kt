package com.rshub.javafx.ui.model.walking

import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.edges.strategies.EdgeTileStrategy
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel

class EdgeModel(from: VertexModel, to: VertexModel, strategy: EdgeStrategy = EdgeStrategy.TILE) : ViewModel() {

    val from = bind { SimpleObjectProperty(this, "from", from) }
    val to = bind { SimpleObjectProperty(this, "to", to) }
    val strategy = bind { SimpleObjectProperty(this, "strategy", strategy) }

    fun toEdge(): Edge {
        val strategy = when(strategy.get()!!) {
            EdgeStrategy.TILE -> EdgeTileStrategy()
            EdgeStrategy.OBJECT -> TODO()
            EdgeStrategy.AGILITY -> TODO()
        }
        val from = this.from.get().toVertex()
        val to = this.to.get().toVertex()
        return Edge(from, to, from.tile.distance(to.tile), strategy)
    }

}