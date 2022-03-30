package com.rshub.api.pathing.web.nodes

import com.rshub.definitions.maps.WorldTile
import kotlinx.serialization.Serializable

@Serializable
data class GraphVertex(val tile: WorldTile) {
    fun distance(vertex: GraphVertex) : Int = tile.distance(vertex.tile)
}