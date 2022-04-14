package com.rshub.javafx.ui.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import kraken.plugin.api.Widget
import tornadofx.ViewModel

class WidgetChildModel(id: Int, widget: Widget? = null) : ViewModel() {

    val id = bind { SimpleIntegerProperty(this, "index", id) }
    val widget = bind { SimpleObjectProperty<Widget>(this, "widget", widget) }

}