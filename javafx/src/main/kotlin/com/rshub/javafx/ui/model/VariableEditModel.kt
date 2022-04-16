package com.rshub.javafx.ui.model

import com.rshub.json.serialization.VariableEditModelSerializer
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import kotlinx.serialization.Serializable
import tornadofx.ViewModel

@Serializable(with = VariableEditModelSerializer::class)
class VariableEditModel(variableName: String, id: Int, type: Type) : ViewModel() {

    val variableName = bind { SimpleStringProperty(this, "variable_name", variableName) }
    val variableId = bind { SimpleIntegerProperty(this, "variable_id", id) }
    val variableType = bind { SimpleObjectProperty(this, "variable_type", type) }


    enum class Type {
        VARBIT,
        VARP
    }
}