package com.rshub.javafx.ui.model

import javafx.beans.property.SimpleFloatProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

class PlayerModel : ViewModel() {

    val tile = SimpleStringProperty(this, "tile", "unknown")
    val serverIndex = SimpleIntegerProperty(this, "server_index", -1)
    val totalLevel = SimpleIntegerProperty(this, "total_level", 10)
    val combatLevel = SimpleIntegerProperty(this, "combat_level", 3)
    val health = SimpleFloatProperty(this, "health", 0.0f)
    val adrenaline = SimpleFloatProperty(this, "adrenaline", 0.0f)

}