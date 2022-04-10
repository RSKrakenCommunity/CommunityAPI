package com.rshub.api.pathing.web

import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.strategy.FixedTileStrategy
import com.rshub.api.pathing.walking.TraversalNode
import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.edges.strategies.EdgeTileStrategy
import com.rshub.api.pathing.web.nodes.GraphVertex

class RuneScapeWeb(val edges: List<Edge>) {
    private val GraphVertex.neighbors: List<GraphVertex>
        get() = edges
            .asSequence()
            .filter { it.from == this || it.to == this }
            .map { listOf(it.from, it.to) }
            .flatten()
            .filterNot { it === this }
            .distinct()
            .toList()

    private val Edge.cost: Double
        get() = costToMoveThrough(this)

    private fun findRoute(from: GraphVertex, to: GraphVertex): Edge? {
        return edges.find {
            (it.from == from && it.to == to)
        }
    }

    private fun findRouteOrDefault(from: GraphVertex, to: GraphVertex): Edge {
        return findRoute(from, to) ?: Edge(from, to, EdgeTileStrategy())
    }

    private fun generatePath(currentPos: GraphVertex, cameFrom: Map<GraphVertex, TraversalNode>): List<TraversalNode> {
        val tn = TraversalNode(currentPos, Edge(currentPos, currentPos, EdgeTileStrategy()))
        val path = mutableListOf(tn)
        var current = tn

        while (cameFrom.containsKey(current.vertex)) {
            current = cameFrom.getValue(current.vertex)
            path.add(0, current)
        }
        return path.toList()
    }

    private fun costToMoveThrough(edge: Edge): Double {
        val cost = LocalPathing.getLocalStepsTo(edge.from.tile, 1, FixedTileStrategy(edge.to.tile), false)
        return edge.strategy.modifyCost(cost).toDouble()
    }

    fun findPath(begin: GraphVertex, end: GraphVertex): Pair<List<TraversalNode>, Double> {
        val cameFrom = mutableMapOf<GraphVertex, TraversalNode>()

        val openVertices = mutableSetOf(begin)
        val closedVertices = mutableSetOf<GraphVertex>()

        val costFromStart = mutableMapOf(begin to 0.0)

        val estimatedTotalCost = mutableMapOf(begin to begin.tile.distance(end.tile).toDouble())

        while (openVertices.isNotEmpty()) {
            val currentPos = openVertices.minByOrNull { estimatedTotalCost.getValue(it) }!!

            // Check if we have reached the finish
            if (currentPos == end) {
                // Backtrack to generate the most efficient path
                val path = generatePath(currentPos, cameFrom)

                // First Route to finish will be optimum route
                return Pair(path, estimatedTotalCost.getValue(end))
            }

            // Mark the current vertex as closed
            openVertices.remove(currentPos)
            closedVertices.add(currentPos)

            for (neighbour in (currentPos.neighbors - closedVertices)) {
                val edge = findRouteOrDefault(from = currentPos, to = neighbour)
                if (edge.blocked()) {
                    println("Edge blocked: ${edge.strategy::class.simpleName}")
                    continue
                }
                val cost: Double = costFromStart.getValue(currentPos) + edge.cost

                if (cost < costFromStart.getOrDefault(neighbour, Double.MAX_VALUE)) {
                    if (!openVertices.contains(neighbour)) {
                        openVertices.add(neighbour)
                    }

                    cameFrom[neighbour] = TraversalNode(currentPos, edge)
                    costFromStart[neighbour] = cost

                    val e = findRouteOrDefault(from = neighbour, to = end)
                    val estimatedRemainingRouteCost = e.cost
                    estimatedTotalCost[neighbour] = cost + estimatedRemainingRouteCost
                }
            }
        }
        return emptyList<TraversalNode>() to Double.POSITIVE_INFINITY
    }
}