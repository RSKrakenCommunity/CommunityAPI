package com.rshub.javafx.ui.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.TreeItem
import kraken.plugin.api.Widget
import tornadofx.ViewModel

class WidgetModel : ViewModel() {

    val groupId = bind { SimpleIntegerProperty(this, "widget_id", -1) }

    val root = bind { SimpleObjectProperty<TreeItem<WidgetChildModel>>(this, "root") }

    val filterText = bind { SimpleBooleanProperty(this, "filter_text", false) }

}