package com.rshub.javafx.ui.model.walking

import com.rshub.api.pathing.web.nodes.GraphVertex
import com.rshub.definitions.maps.WorldTile
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleSetProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class VertexModel(id: Int, tile: WorldTile) : ViewModel() {

    val id = bind { SimpleIntegerProperty(this, "id", id) }
    val tile = bind { SimpleObjectProperty(this, "tile", tile) }
    val edges = bind { SimpleSetProperty<EdgeModel>(this, "edges", FXCollections.observableSet(mutableSetOf())) }

    fun toVertex(): GraphVertex {
        return GraphVertex(tile.get())
    }

}