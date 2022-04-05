package com.rshub.javafx.ui.model.walking

import com.rshub.api.pathing.WalkHelper
import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.edges.strategies.DoorStrategy
import com.rshub.api.pathing.web.edges.strategies.EdgeTileStrategy
import com.rshub.api.pathing.web.edges.strategies.NpcStrategy
import com.rshub.api.pathing.web.edges.strategies.ObjectStrategy
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleSetProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class WebWalkingModel : ViewModel() {

    val showOnMinimap = bind { SimpleBooleanProperty(this, "show_on_minimap", false) }
    val autoUpdate = bind { SimpleBooleanProperty(this, "auto_update", true) }
    val autoLink = bind { SimpleBooleanProperty(this, "auto_link", false) }

    val vertices = bind { SimpleSetProperty<VertexModel>(this, "vertices", FXCollections.observableSet(mutableSetOf())) }

    val edges = bind { SimpleSetProperty<EdgeModel>(this, "edges", FXCollections.observableSet(mutableSetOf())) }

    val selectedVertex = bind { SimpleObjectProperty<VertexModel>(this, "selected_vertex") }
    val selectedEdge = bind { SimpleObjectProperty<EdgeModel>(this, "selected_edge") }

    fun reload() {
        val graph = WalkHelper.getGraph()
        vertices.set(FXCollections.observableSet(graph.getAllVertices().mapIndexed { index, graphVertex -> VertexModel(index, graphVertex)}.toMutableSet()))
        graph.getAllEdges().forEach { addEdgeModel(it) }
    }

    private fun addEdgeModel(edge: Edge) {
        val vmFrom = vertices.find { it.tile.get() == edge.from.tile }!!
        val vmTo = vertices.find { it.tile.get() == edge.to.tile }!!
        vmFrom.edges.add(EdgeModel(vmFrom, vmTo, edge))
    }

    fun update() {
        val graph = WalkHelper.getGraph()
        graph.setVertices(vertices.map { it.update(); it.item })
        graph.setEdges(vertices.flatMap { it.edges }.map { it.update(); it.edge })
    }
}