package com.rshub.api.pathing.web.edges

import com.rshub.api.pathing.web.nodes.GraphVertex

class Edge(
    val from: GraphVertex,
    val to: GraphVertex,
    val cost: Int,
    val strategy: EdgeStrategy
) {
    fun traverse() = strategy.traverse(this)
    fun blocked() = strategy.blocked()
}

