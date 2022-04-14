package com.rshub.javafx.ui.model.walking

import javafx.beans.property.*
import javafx.collections.FXCollections
import javafx.collections.transformation.SortedList
import tornadofx.ViewModel
import tornadofx.onChange

class WalkingModel : ViewModel() {

    val locations = bind { SimpleListProperty<LocationModel>(this, "locations", FXCollections.observableArrayList()) }
    val selectedLocation = bind { SimpleObjectProperty<LocationModel>(this, "selected_location") }

    val locationName = bind { SimpleStringProperty(this, "location_name") }
    val isBank = bind { SimpleBooleanProperty(this, "is_bank", false) }

    val showBanksOnMinimap = bind { SimpleBooleanProperty(this, "show_bank_on_minimap", false) }

    init {
        selectedLocation.onChange {
            if(it != null) {
                locationName.set(it.name.get())
                isBank.set(it.bank.get())
            }
        }
    }

}