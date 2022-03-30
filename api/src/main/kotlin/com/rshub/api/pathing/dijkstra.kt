package com.rshub.api.pathing

import com.rshub.api.pathing.web.Graph
import com.rshub.api.pathing.web.nodes.GraphVertex

fun shortestPath(graph: Graph, from: GraphVertex, destination: GraphVertex): Pair<List<GraphVertex>, Double> {
    return dijkstra(graph, from, destination)[destination] ?: (emptyList<GraphVertex>() to Double.POSITIVE_INFINITY)
}

private fun dijkstra(graph: Graph, from: GraphVertex, destination: GraphVertex? = null): Map<GraphVertex, Pair<List<GraphVertex>, Double>> {
    val unvisitedSet = graph.getAllVertices().toMutableSet()
    val distances = graph.getAllVertices().map { it to Double.POSITIVE_INFINITY }.toMap().toMutableMap()
    val paths = mutableMapOf<GraphVertex, List<GraphVertex>>()
    distances[from] = 0.0

    var current = from

    while (unvisitedSet.isNotEmpty() && unvisitedSet.contains(destination)) {
        graph.adjacentVertices(current).forEach { adjacent ->
            val distance = graph.getDistance(current, adjacent).toDouble()
            if (distances[current]!! + distance < distances[adjacent]!!) {
                distances[adjacent] = distances[current]!! + distance
                paths[adjacent] = paths.getOrDefault(current, listOf(current)) + listOf(adjacent)
            }
        }

        unvisitedSet.remove(current)

        if (current == destination || unvisitedSet.all { distances[it]!!.isInfinite() }) {
            break
        }

        if (unvisitedSet.isNotEmpty()) {
            current = unvisitedSet.minByOrNull { distances[it]!! }!!
        }
    }

    return paths.mapValues { entry -> entry.value to distances[entry.key]!! }
}