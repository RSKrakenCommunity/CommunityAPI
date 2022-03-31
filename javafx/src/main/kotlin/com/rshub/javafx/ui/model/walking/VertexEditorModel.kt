package com.rshub.javafx.ui.model.walking

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel

class VertexEditorModel : ViewModel() {

    val usePlayerTile = bind { SimpleBooleanProperty(this, "use_player_tile", true) }
    val tileX = bind { SimpleIntegerProperty(this, "tile_x", -1) }
    val tileY = bind { SimpleIntegerProperty(this, "tile_y", -1) }
    val tileZ = bind { SimpleIntegerProperty(this, "tile_z", -1) }

    val strategy = bind { SimpleObjectProperty(this, "edge_strategy", EdgeStrategy.TILE) }

}