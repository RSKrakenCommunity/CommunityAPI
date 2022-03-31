package com.rshub.javafx.ui.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

class ScriptingModel : ViewModel() {

    val script = bind { SimpleStringProperty(this, "script", "") }

}