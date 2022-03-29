package com.rshub.javafx.ui.model

import javafx.beans.property.SimpleStringProperty
import kraken.plugin.api.Kraken
import tornadofx.ViewModel

class GlobalModel : ViewModel() {

    val title = SimpleStringProperty(this, "title", Kraken.getEmail())

}