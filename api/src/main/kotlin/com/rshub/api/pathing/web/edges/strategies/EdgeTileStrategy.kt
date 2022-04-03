package com.rshub.api.pathing.web.edges.strategies

import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.strategy.FixedTileStrategy
import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.edges.EdgeStrategy
import com.rshub.definitions.maps.WorldTile.Companion.expand
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import kotlinx.serialization.Serializable
import kraken.plugin.api.Move
import kraken.plugin.api.Players

@Serializable
class EdgeTileStrategy : EdgeStrategy {
    override fun traverse(edge: Edge): Boolean {
        Move.to(edge.from.tile)
        return true
    }

    override fun reached(edge: Edge): Boolean {
        return edge.from.tile.expand(4).contains(Players.self())
    }

    override fun modifyCost(cost: Int): Int {
        return cost
    }

    override fun blocked(edge: Edge): Boolean {
        return false
    }
}