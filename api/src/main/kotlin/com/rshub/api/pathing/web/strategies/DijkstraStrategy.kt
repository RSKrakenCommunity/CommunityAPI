package com.rshub.api.pathing.web.strategies

import com.rshub.api.pathing.web.PathStrategy
import com.rshub.api.pathing.web.Traversal
import com.rshub.api.pathing.web.TraversalNode

class DijkstraStrategy : PathStrategy {
    override fun findPath(start: TraversalNode, traversal: Traversal): List<TraversalNode> {
        val distances = traversal.nodes.associateWith { Int.MAX_VALUE }.toMutableMap()
        val path = mutableListOf<TraversalNode>()
        distances[start] = 0


        return emptyList()
    }
}