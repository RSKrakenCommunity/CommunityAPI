package com.rshub.javafx.ui.model

import com.rshub.api.definitions.CacheHelper
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel
import tornadofx.onChange

class VariableModel(id: Int, text: String, value: Int = -1, isVarp: Boolean = true) : ViewModel() {

    val variableId = bind { SimpleIntegerProperty(this, "variable_id", id) }
    val isVarp = bind { SimpleBooleanProperty(this, "is_varp", isVarp) }
    val displayText = bind { SimpleStringProperty(this, "display_text", text) }
    val value = bind { SimpleIntegerProperty(this, "value", value) }

    val varbits = bind { SimpleListProperty<VariableModel>(this, "varbits", FXCollections.observableArrayList()) }

    init {
        this.value.onChange {
            if(this.isVarp.get()) {
                varbits.setAll(
                    CacheHelper.findVarbitsFor(variableId.get())
                    .map { def -> VariableModel(def.id, "", CacheHelper.getVarbitValue(def.id), false) })
            } else if(varbits.isNotEmpty()) {
                varbits.clear()
            }
        }
    }

}