package com.rshub.javafx.ui.model.walking

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleSetProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class WalkingModel : ViewModel() {

    val locations = bind { SimpleSetProperty<LocationModel>(this, "locations", FXCollections.observableSet(mutableSetOf())) }
    val selectedLocation = bind { SimpleObjectProperty<LocationModel>(this, "selected_location") }

    val locationName = bind { SimpleStringProperty(this, "location_name") }

}