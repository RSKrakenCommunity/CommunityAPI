package com.rshub.javafx.ui.model.containers

import com.rshub.api.containers.Container
import com.rshub.javafx.ui.model.RSItemModel
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleMapProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class ContainerFilterModel : ViewModel() {

    val containers = bind { SimpleMapProperty<Int, ContainerModel>(this, "containers", FXCollections.observableHashMap()) }

    val selectedContainer = bind { SimpleObjectProperty<ContainerModel>(this, "selected_container") }

    val items = bind { SimpleListProperty<RSItemModel>(this, "items", FXCollections.observableArrayList()) }
    val selectedItem = bind { SimpleObjectProperty<RSItemModel>(this, "selected_item") }

}