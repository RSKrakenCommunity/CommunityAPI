package com.rshub.imgui.nodes

import javafx.beans.property.SimpleStringProperty

interface Labeled : Node {

    val labelProperty: SimpleStringProperty
    val text: String get() = labelProperty.value

}