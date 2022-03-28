package com.rshub.javafx.ui.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

class PlayerModel : ViewModel() {

    val tile = SimpleStringProperty(this, "tile", "unknown")

}