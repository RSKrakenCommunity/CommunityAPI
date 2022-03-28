package com.rshub.api.pathing.web

import com.rshub.definitions.maps.WorldTile

class TraversalNode(val id: Int, val tile: WorldTile) {

    val edges = mutableListOf<Edge>()

}