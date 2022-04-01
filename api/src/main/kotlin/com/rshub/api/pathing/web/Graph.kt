package com.rshub.api.pathing.web

import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.edges.EdgeStrategy
import com.rshub.api.pathing.web.edges.strategies.EdgeTileStrategy
import com.rshub.api.pathing.web.nodes.GraphVertex
import kotlinx.serialization.Serializable

@Serializable
class Graph {
    private val edges = mutableSetOf<Edge>()
    private val vertices = mutableSetOf<GraphVertex>()

    fun addVertex(node: GraphVertex): Graph {
        vertices += node
        return this
    }

    fun addArc(pair: Pair<GraphVertex, GraphVertex>, strategy: EdgeStrategy, directed: Boolean = false) : Graph {
        val (from, to) = pair
        addVertex(from)
        addVertex(to)
        edges.add(Edge(from, to, strategy))
        if (!directed) {
            edges.add(Edge(to, from, strategy))
        }
        return this
    }

    fun getAllVertices(): Set<GraphVertex> {
        return vertices.toSet()
    }

    fun getAllEdges() : Set<Edge> {
        return edges.toSet()
    }

    fun adjacentVertices(vertex: GraphVertex): Set<GraphVertex> {
        return edges.filter { it.from == vertex && it.strategy is EdgeTileStrategy }.map { it.to }.toSet()
    }

    fun setVertices(vertices: List<GraphVertex>) {

        this.vertices.clear()
        this.vertices.addAll(vertices)
    }

    fun setEdges(edges: List<Edge>) {
        this.edges.clear()
        this.edges.addAll(edges)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Graph

        if (edges != other.edges) return false
        if (vertices != other.vertices) return false

        return true
    }

    override fun hashCode(): Int {
        var result = edges.hashCode()
        result = 31 * result + vertices.hashCode()
        return result
    }

    companion object {
        fun Graph.toWeb() = RuneScapeWeb(edges.toList())
    }
}