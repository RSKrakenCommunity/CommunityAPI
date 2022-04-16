package com.rshub.javafx.ui.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class VariableEditorModel : ViewModel() {

    val variables = bind { SimpleListProperty<VariableEditModel>(this, "variables", FXCollections.observableArrayList()) }

    val selectedVariable = bind { SimpleObjectProperty<VariableEditModel>(this, "selected_variable") }

    val varName = bind { SimpleStringProperty(this, "var_name", "") }
    val varId = bind { SimpleIntegerProperty(this, "var_id", -1) }
    val type = bind { SimpleObjectProperty(this, "type", VariableEditModel.Type.VARP) }

}