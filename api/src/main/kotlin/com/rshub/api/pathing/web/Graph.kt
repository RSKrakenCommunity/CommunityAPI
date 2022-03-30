package com.rshub.api.pathing.web

import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.nodes.GraphVertex

class Graph {
    private val edges = mutableSetOf<Edge>()
    private val vertices = mutableSetOf<GraphVertex>()

    fun addVertex(node: GraphVertex): Graph {
        vertices += node
        return this
    }

    fun addArc(pair: Pair<GraphVertex, GraphVertex>, edge: Edge, directed: Boolean = false) : Graph {
        val (from, to) = pair
        addVertex(from)
        addVertex(to)
        edges.add(edge)
        if (!directed) {
            edges.add(Edge(edge.to, edge.from, edge.cost, edge.strategy))
        }
        return this
    }

    fun getAllVertices(): Set<GraphVertex> {
        return vertices.toSet()
    }

    fun adjacentVertices(vertex: GraphVertex): Set<GraphVertex> {
        return edges.filter { it.from == vertex }.map { it.to }.toSet()
    }

    fun getDistance(from: GraphVertex, to: GraphVertex): Int {
        return edges
            .filter { it.from == from && it.to == to }
            .map { it.cost }
            .first()
    }
}