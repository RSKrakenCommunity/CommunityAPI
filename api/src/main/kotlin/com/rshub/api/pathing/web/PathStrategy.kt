package com.rshub.api.pathing.web

interface PathStrategy {

    fun findPath(start: TraversalNode, traversal: Traversal) : List<TraversalNode>

}