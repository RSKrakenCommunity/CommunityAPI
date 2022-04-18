package com.rshub.javafx.ui.model

import javafx.beans.property.*
import kraken.plugin.api.Spirit
import tornadofx.ViewModel

class PlayerModel : ViewModel() {

    val tile = SimpleStringProperty(this, "tile", "unknown")
    val localTile = SimpleStringProperty(this, "local_tile", "unknown")
    val isMoving = SimpleBooleanProperty(this, "is_moving", false)
    val animationId = SimpleIntegerProperty(this, "animation_id", -1)
    val serverIndex = SimpleIntegerProperty(this, "server_index", -1)
    val totalLevel = SimpleIntegerProperty(this, "total_level", 10)
    val combatLevel = SimpleIntegerProperty(this, "combat_level", 3)
    val health = SimpleFloatProperty(this, "health", 0.0f)
    val adrenaline = SimpleFloatProperty(this, "adrenaline", 0.0f)
    val interactingIndex = SimpleIntegerProperty(this, "interacting_index", -1)
    val interacting = SimpleObjectProperty<Spirit>(this, "interacting")

    val attackingSpirit = bind { SimpleIntegerProperty(this, "attacking_spirit", -1) }

    val inCombat = bind { SimpleBooleanProperty(this, "in_combat", false) }

}