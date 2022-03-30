package com.rshub.api.pathing.web.edges.strategies

import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.edges.EdgeStrategy
import kraken.plugin.api.Move

class EdgeTileStrategy : EdgeStrategy {
    override fun traverse(edge: Edge): Boolean {
        Move.to(edge.to.tile)
        return true
    }

    override fun blocked(): Boolean {
        return false
    }
}