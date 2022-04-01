package com.rshub.api.pathing.web.edges

import com.rshub.api.pathing.web.nodes.GraphVertex
import kotlinx.serialization.Serializable

@Serializable
class Edge(
    val from: GraphVertex,
    val to: GraphVertex,
    val strategy: EdgeStrategy
) {

    val dist: Int = from.distance(to)

    fun traverse() = strategy.traverse(this)
    fun blocked() = strategy.blocked(this)
    fun reached() = strategy.reached(this)
}

