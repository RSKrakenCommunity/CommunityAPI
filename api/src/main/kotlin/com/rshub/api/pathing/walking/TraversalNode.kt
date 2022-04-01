package com.rshub.api.pathing.walking

import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.nodes.GraphVertex

class TraversalNode(val vertex: GraphVertex, val edge: Edge) {

    fun traverse() = edge.traverse()

}