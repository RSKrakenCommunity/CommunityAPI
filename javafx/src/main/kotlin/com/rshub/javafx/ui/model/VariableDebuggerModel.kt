package com.rshub.javafx.ui.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleMapProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class VariableDebuggerModel : ViewModel() {

    val varps = bind { SimpleMapProperty<Int, VariableModel>(this, "varps", FXCollections.observableHashMap()) }
    val varbits = bind { SimpleListProperty<VariableModel>(this, "varbits", FXCollections.observableArrayList()) }

    val selectedVarp = bind { SimpleObjectProperty<VariableModel>(this, "selected_varp") }
    val selectedVarbit = bind { SimpleObjectProperty<VariableModel>(this, "selected_varbit") }

    val isScanMode = bind { SimpleBooleanProperty(this, "scan_mode", false) }

}